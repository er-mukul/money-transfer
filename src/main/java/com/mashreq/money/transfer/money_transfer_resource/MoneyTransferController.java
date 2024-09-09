package com.mashreq.money.transfer.money_transfer_resource;

import com.mashreq.money.transfer.money_transfer_resource.model.request.MoneyTransferRequestDto;
import com.mashreq.money.transfer.money_transfer_resource.model.response.MoneyTransferResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/money-transfer", produces = "application/json")
@Validated
public class MoneyTransferController {
    private final MoneyTransferHandler moneyTransferHandler;

    @PostMapping
    @Validated(MoneyTransferRequestDto.ValidateMoneyTransferRequest.class)
    public ResponseEntity<MoneyTransferResponseDto> performMoneyTransfer(@Valid @RequestBody MoneyTransferRequestDto requestDto) {
           return moneyTransferHandler.performMoneyTransfer(requestDto);

    }
}
