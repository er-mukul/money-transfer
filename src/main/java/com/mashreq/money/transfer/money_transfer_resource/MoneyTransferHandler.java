package com.mashreq.money.transfer.money_transfer_resource;

import com.mashreq.money.transfer.money_transfer_resource.mapper.MoneyTransferMapper;
import com.mashreq.money.transfer.money_transfer_resource.model.request.MoneyTransferRequestDto;
import com.mashreq.money.transfer.money_transfer_resource.model.response.MoneyTransferResponseDto;
import com.mashreq.money.transfer.money_transfer_service.MoneyTransferService;
import com.mashreq.money.transfer.money_transfer_service.model.response.MoneyTransferResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoneyTransferHandler {
    private final MoneyTransferService moneyTransferService;
    private final MoneyTransferMapper moneyTransferMapper;

    public ResponseEntity<MoneyTransferResponseDto> performMoneyTransfer(MoneyTransferRequestDto requestDto) {
        MoneyTransferResponse moneyTransferResponse = moneyTransferService.performMoneyTransfer(moneyTransferMapper.mapRequest(requestDto));
        return ResponseEntity.ok(moneyTransferMapper.map(moneyTransferResponse));
    }
}
