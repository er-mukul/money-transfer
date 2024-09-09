package com.mashreq.money.transfer.core_money_transfer_service.impl;

import com.mashreq.money.transfer.core_money_transfer_service.CoreMoneyTransferService;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferRequest;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferResponse;
import com.mashreq.money.transfer.ports.MoneyTransferPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoreMoneyTransferServiceImpl implements CoreMoneyTransferService {
    private final MoneyTransferPort moneyTransferPort;

    @Override
    public CoreMoneyTransferResponse performMoneyTransfer(CoreMoneyTransferRequest request) {
        return moneyTransferPort.performMoneyTransfer(request);
    }
}
