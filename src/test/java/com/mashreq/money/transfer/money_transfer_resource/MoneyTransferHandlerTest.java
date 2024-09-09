package com.mashreq.money.transfer.money_transfer_resource;

import com.mashreq.money.transfer.money_transfer_resource.mapper.MoneyTransferMapper;
import com.mashreq.money.transfer.money_transfer_resource.model.request.MoneyTransferRequestDto;
import com.mashreq.money.transfer.money_transfer_resource.model.response.MoneyTransferResponseDto;
import com.mashreq.money.transfer.money_transfer_service.MoneyTransferService;
import com.mashreq.money.transfer.money_transfer_service.model.request.MoneyTransferRequest;
import com.mashreq.money.transfer.money_transfer_service.model.response.MoneyTransferResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MoneyTransferHandler.class})
@ExtendWith(SpringExtension.class)
class MoneyTransferHandlerTest {
    @Autowired
    private MoneyTransferHandler moneyTransferHandler;

    @MockBean
    private MoneyTransferService moneyTransferService;

    @MockBean
    private MoneyTransferMapper moneyTransferMapper;

    @Test
    void testPerformMoneyTransfer() {
        MockitoAnnotations.openMocks(this);
        when(moneyTransferService.performMoneyTransfer(any())).thenReturn(
                MoneyTransferResponse.builder().build());
        when(moneyTransferMapper.mapRequest(any())).thenReturn(MoneyTransferRequest.builder().build());

        ResponseEntity<MoneyTransferResponseDto> response = moneyTransferHandler.performMoneyTransfer(
                MoneyTransferRequestDto.builder().build()
        );

        Assertions.assertEquals(200,response.getStatusCode().value());
        verify(moneyTransferService,times(1)).performMoneyTransfer(any());
    }
}