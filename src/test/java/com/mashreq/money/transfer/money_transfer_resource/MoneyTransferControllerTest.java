package com.mashreq.money.transfer.money_transfer_resource;

import com.mashreq.money.transfer.money_transfer_resource.model.request.MoneyTransferRequestDto;
import com.mashreq.money.transfer.money_transfer_resource.model.response.MoneyTransferResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {MoneyTransferController.class})
@ExtendWith(SpringExtension.class)
class MoneyTransferControllerTest {
    @Autowired
    private MoneyTransferController moneyTransferController;

    @MockBean
    private MoneyTransferHandler moneyTransferHandler;

    @Test
    void testPerformMoneyTransfer() {
        MockitoAnnotations.openMocks(this);
        when(moneyTransferHandler.performMoneyTransfer(any())).thenReturn(
                ResponseEntity.ok(MoneyTransferResponseDto.builder().build()));

        ResponseEntity<MoneyTransferResponseDto> response = moneyTransferController.performMoneyTransfer(
                MoneyTransferRequestDto.builder().build()
        );

        Assertions.assertEquals(200,response.getStatusCode().value());
        verify(moneyTransferHandler,times(1)).performMoneyTransfer(any());
    }

}