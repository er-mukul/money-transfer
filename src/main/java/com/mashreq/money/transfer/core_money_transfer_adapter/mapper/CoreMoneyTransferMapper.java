package com.mashreq.money.transfer.core_money_transfer_adapter.mapper;

import com.mashreq.money.transfer.commons.enums.MoneyTransferStatus;
import com.mashreq.money.transfer.core_money_transfer_adapter.model.request.DebitCreditRequestData;
import com.mashreq.money.transfer.core_money_transfer_adapter.model.request.CoreMoneyTransferRequestData;
import com.mashreq.money.transfer.core_money_transfer_adapter.model.response.CoreMoneyTransferResponseData;
import com.mashreq.money.transfer.core_money_transfer_service.model.DebitCreditData;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferRequest;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

@Component
@AllArgsConstructor
public class CoreMoneyTransferMapper {
    public CoreMoneyTransferRequestData mapRequest(CoreMoneyTransferRequest request) {
            return CoreMoneyTransferRequestData.builder()
                    .creditLegData(getDebitCreditRequestData(request.getCreditLegData()))
                    .debitLegData(getDebitCreditRequestData(request.getDebitLegData()))
                    .notes(request.getNotes())
                    .reason(request.getReason())
                    .transactionNumber(generateTransactionNumber())
                    .build();
    }

    public CoreMoneyTransferResponse map(CoreMoneyTransferResponseData responseData, String transactionNumber) {
        if(!Objects.nonNull(responseData)) return null;
        return CoreMoneyTransferResponse.builder()
                .status(MoneyTransferStatus.getByName(responseData.getStatus()))
                .transactionNumber(transactionNumber)
                .build();
    }

    private DebitCreditRequestData getDebitCreditRequestData(DebitCreditData creditLegData) {
        return DebitCreditRequestData.builder()
                .accountNumber(creditLegData.getAccountNumber())
                .amount(creditLegData.getAmount())
                .currency(creditLegData.getCurrency())
                .build();
    }

    private String generateTransactionNumber() {
        Random random = new SecureRandom();
        int number = random.nextInt(900000) + 100000;
        return String.valueOf(number);

    }
}
