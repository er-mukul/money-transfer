package com.mashreq.money.transfer.money_transfer_service.impl;

import com.mashreq.money.transfer.accounts_service.AccountsService;
import com.mashreq.money.transfer.accounts_service.model.AccountDetails;
import com.mashreq.money.transfer.commons.enums.MoneyTransferStatus;
import com.mashreq.money.transfer.commons.enums.TransactionFailureReason;
import com.mashreq.money.transfer.core_money_transfer_service.CoreMoneyTransferService;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferRequest;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferResponse;
import com.mashreq.money.transfer.core_money_transfer_service.model.DebitCreditData;
import com.mashreq.money.transfer.database_adapter.model.TransactionDetails;
import com.mashreq.money.transfer.money_transfer_service.MoneyTransferService;
import com.mashreq.money.transfer.money_transfer_service.model.request.MoneyTransferRequest;
import com.mashreq.money.transfer.money_transfer_service.model.response.MoneyTransferResponse;
import com.mashreq.money.transfer.money_transfer_service.model.response.MoneyTransferResponseData;
import com.mashreq.money.transfer.ports.DatabasePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.mashreq.money.transfer.commons.AppConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {
    private final AccountsService accountsService;
    private final CoreMoneyTransferService coreMoneyTransferService;
    private final DatabasePort databasePort;

    @Override
    public MoneyTransferResponse performMoneyTransfer(MoneyTransferRequest request) {
        AccountDetails accountDetails = accountsService.fetchAccountDetails(request.getCif(), request.getSourceAccount());

        // Validate account details
        if (accountDetails.getBalance() == null) {
            return buildErrorResponse();
        }

        String failureReason = null;
        String transactionStatus = null;
        String timestamp = LocalDateTime.now().toString();
        // Perform validation checks
        if (accountDetails.getBalance().compareTo(request.getAmount()) < 0) {
            failureReason = TransactionFailureReason.BALANCE.name();
            transactionStatus = MoneyTransferStatus.FAILED.name();
        } else if (!accountDetails.getCurrency().equalsIgnoreCase(request.getCurrency())) {
            failureReason = TransactionFailureReason.INVALID_ACCOUNT.name();
            transactionStatus = MoneyTransferStatus.FAILED.name();
        } else if (accountDetails.getLimits().getDaily().compareTo(request.getAmount()) < 0) {
            failureReason = TransactionFailureReason.DAILY_LIMIT.name();
            transactionStatus = MoneyTransferStatus.FAILED.name();
        } else if (accountDetails.getLimits().getMonthly().compareTo(request.getAmount()) < 0) {
            failureReason = TransactionFailureReason.MONTHLY_LIMIT.name();
            transactionStatus = MoneyTransferStatus.FAILED.name();
        }

        // If validation fails, return the error response
        if (transactionStatus != null) {
            return buildErrorResponse(failureReason, transactionStatus, timestamp);
        }

        // Perform core money transfer
        CoreMoneyTransferResponse coreResponse = coreMoneyTransferService.performMoneyTransfer(
                CoreMoneyTransferRequest.builder()
                        .notes(request.getNotes())
                        .reason(request.getReason())
                        .creditLegData(DebitCreditData.builder().accountNumber(request.getDestinationAccount()).build())
                        .debitLegData(DebitCreditData.builder()
                                .accountNumber(request.getSourceAccount())
                                .amount(request.getAmount())
                                .currency(request.getCurrency())
                                .build())
                        .build()
        );

        // Determine transaction status based on core response
        if (coreResponse.getStatus() == null) {
            return buildErrorResponse();
        }

        switch (coreResponse.getStatus()) {
            case DEBITED:
                transactionStatus = TRANSACTION_STATUS_PENDING;
                break;
            case CREDITED:
                transactionStatus = TRANSACTION_STATUS_EXECUTED;
                break;
            default:
                transactionStatus = MoneyTransferStatus.FAILED.name();
                failureReason = TransactionFailureReason.INVALID_ACCOUNT.name();
        }

        databasePort.saveTransactionDetails(TransactionDetails.builder()
                .timestamp(LocalDateTime.now())
                .transactionStatus(transactionStatus)
                .transactionNumber(coreResponse.getTransactionNumber())
                .build());

        return buildSuccessResponse(failureReason, transactionStatus, timestamp, coreResponse.getTransactionNumber());
    }

    private MoneyTransferResponse buildErrorResponse() {
        return MoneyTransferResponse.builder().status(ERROR).build();
    }

    private MoneyTransferResponse buildErrorResponse(String failureReason, String transactionStatus, String timestamp) {
        return MoneyTransferResponse.builder().status(SUCCESS)
                .data(MoneyTransferResponseData.builder()
                        .failureReason(failureReason)
                        .transactionStatus(transactionStatus)
                        .timestamp(timestamp)
                        .build())
                .build();
    }

    private MoneyTransferResponse buildSuccessResponse(String failureReason, String transactionStatus,
                                                       String timestamp, String transactionNumber) {
        return MoneyTransferResponse.builder().status(SUCCESS)
                .data(MoneyTransferResponseData.builder()
                        .failureReason(failureReason)
                        .transactionStatus(transactionStatus)
                        .transactionNumber(transactionNumber)
                        .timestamp(timestamp)
                        .build())
                .build();
    }
}
