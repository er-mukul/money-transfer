package com.mashreq.money.transfer.money_transfer_service.impl;

import com.mashreq.money.transfer.accounts_service.AccountsService;
import com.mashreq.money.transfer.accounts_service.model.AccountDetails;
import com.mashreq.money.transfer.accounts_service.model.TransferLimits;
import com.mashreq.money.transfer.commons.enums.MoneyTransferStatus;
import com.mashreq.money.transfer.commons.enums.TransactionFailureReason;
import com.mashreq.money.transfer.core_money_transfer_service.CoreMoneyTransferService;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferResponse;
import com.mashreq.money.transfer.database_adapter.model.TransactionDetails;
import com.mashreq.money.transfer.money_transfer_service.model.request.MoneyTransferRequest;
import com.mashreq.money.transfer.money_transfer_service.model.response.MoneyTransferResponse;
import com.mashreq.money.transfer.ports.DatabasePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static com.mashreq.money.transfer.commons.AppConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MoneyTransferServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MoneyTransferServiceImplTest {
    @Autowired
    private MoneyTransferServiceImpl moneyTransferService;

    @MockBean
    private AccountsService accountsService;

    @MockBean
    private CoreMoneyTransferService coreMoneyTransferService;

    @MockBean
    private DatabasePort databasePort;

    @Test
    void testPerformMoneyTransferWhenAccountServiceFails() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").build()
        );

        Assertions.assertEquals(ERROR,response.getStatus());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
    }

    @Test
    void testPerformMoneyTransferWhenAccountBalanceLessThanRequestedAmount() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().balance(BigDecimal.TEN).build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").amount(BigDecimal.valueOf(50)).build()
        );

        Assertions.assertEquals(SUCCESS,response.getStatus());
        Assertions.assertEquals(TransactionFailureReason.BALANCE.name(),response.getData().getFailureReason());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
    }

    @Test
    void testPerformMoneyTransferWhenCurrencyIsDifferent() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().balance(BigDecimal.TEN).currency("USD").build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").amount(BigDecimal.valueOf(5))
                        .currency("AED").build()
        );

        Assertions.assertEquals(SUCCESS,response.getStatus());
        Assertions.assertEquals(TransactionFailureReason.INVALID_ACCOUNT.name(),response.getData().getFailureReason());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
    }

    @Test
    void testPerformMoneyTransferWhenDailyLimitIsLessThanRequestedAmount() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().balance(BigDecimal.TEN).currency("USD").limits(TransferLimits.builder()
                                .daily(BigDecimal.valueOf(2))
                        .build()).build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").amount(BigDecimal.valueOf(5))
                        .currency("USD").build()
        );

        Assertions.assertEquals(SUCCESS,response.getStatus());
        Assertions.assertEquals(TransactionFailureReason.DAILY_LIMIT.name(),response.getData().getFailureReason());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
    }

    @Test
    void testPerformMoneyTransferWhenMonthlyLimitIsLessThanRequestedAmount() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().balance(BigDecimal.TEN).currency("USD").limits(TransferLimits.builder()
                        .daily(BigDecimal.valueOf(12))
                                .monthly(BigDecimal.valueOf(4))
                        .build()).build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").amount(BigDecimal.valueOf(5))
                        .currency("USD").build()
        );

        Assertions.assertEquals(SUCCESS,response.getStatus());
        Assertions.assertEquals(TransactionFailureReason.MONTHLY_LIMIT.name(),response.getData().getFailureReason());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
    }

    @Test
    void testPerformMoneyTransferWhenCoreMoneyTransferFails() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().balance(BigDecimal.TEN).currency("USD").limits(TransferLimits.builder()
                        .daily(BigDecimal.valueOf(100))
                        .monthly(BigDecimal.valueOf(120))
                        .build()).build());
        when(coreMoneyTransferService.performMoneyTransfer(any())).thenReturn(CoreMoneyTransferResponse.builder().build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").amount(BigDecimal.valueOf(5))
                        .currency("USD").build()
        );

        Assertions.assertEquals(ERROR,response.getStatus());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
        verify(coreMoneyTransferService,times(1)).performMoneyTransfer(any());
    }

    @Test
    void testPerformMoneyTransferWhenCoreMoneyTransferDEBITED() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().balance(BigDecimal.TEN).currency("USD").limits(TransferLimits.builder()
                        .daily(BigDecimal.valueOf(100))
                        .monthly(BigDecimal.valueOf(120))
                        .build()).build());
        when(coreMoneyTransferService.performMoneyTransfer(any())).thenReturn(CoreMoneyTransferResponse.builder()
                .transactionNumber("123456").status(MoneyTransferStatus.DEBITED).build());

        when(databasePort.saveTransactionDetails(any())).thenReturn(TransactionDetails.builder().build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").amount(BigDecimal.valueOf(5))
                        .currency("USD").build()
        );

        Assertions.assertEquals(SUCCESS,response.getStatus());
        Assertions.assertEquals(TRANSACTION_STATUS_PENDING,response.getData().getTransactionStatus());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
        verify(coreMoneyTransferService,times(1)).performMoneyTransfer(any());
    }

    @Test
    void testPerformMoneyTransferWhenCoreMoneyTransferCREDITED() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().balance(BigDecimal.TEN).currency("USD").limits(TransferLimits.builder()
                        .daily(BigDecimal.valueOf(100))
                        .monthly(BigDecimal.valueOf(120))
                        .build()).build());
        when(coreMoneyTransferService.performMoneyTransfer(any())).thenReturn(CoreMoneyTransferResponse.builder()
                .transactionNumber("123456").status(MoneyTransferStatus.CREDITED).build());

        when(databasePort.saveTransactionDetails(any())).thenReturn(TransactionDetails.builder().build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").amount(BigDecimal.valueOf(5))
                        .currency("USD").build()
        );

        Assertions.assertEquals(SUCCESS,response.getStatus());
        Assertions.assertEquals(TRANSACTION_STATUS_EXECUTED,response.getData().getTransactionStatus());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
        verify(coreMoneyTransferService,times(1)).performMoneyTransfer(any());
    }

    @Test
    void testPerformMoneyTransferWhenCoreMoneyTransferFAILED() {
        MockitoAnnotations.openMocks(this);
        when(accountsService.fetchAccountDetails(anyString(), anyString())).thenReturn(
                AccountDetails.builder().balance(BigDecimal.TEN).currency("USD").limits(TransferLimits.builder()
                        .daily(BigDecimal.valueOf(100))
                        .monthly(BigDecimal.valueOf(120))
                        .build()).build());
        when(coreMoneyTransferService.performMoneyTransfer(any())).thenReturn(CoreMoneyTransferResponse.builder()
                .transactionNumber("123456").status(MoneyTransferStatus.FAILED).build());

        when(databasePort.saveTransactionDetails(any())).thenReturn(TransactionDetails.builder().build());

        MoneyTransferResponse response = moneyTransferService.performMoneyTransfer(
                MoneyTransferRequest.builder().cif("4444444444").sourceAccount("011111111111").amount(BigDecimal.valueOf(5))
                        .currency("USD").build()
        );

        Assertions.assertEquals(SUCCESS,response.getStatus());
        Assertions.assertEquals(MoneyTransferStatus.FAILED.name(),response.getData().getTransactionStatus());
        verify(accountsService,times(1)).fetchAccountDetails(anyString(), anyString());
        verify(coreMoneyTransferService,times(1)).performMoneyTransfer(any());
    }
}