package com.mashreq.money.transfer.money_transfer_service;

import com.mashreq.money.transfer.money_transfer_service.model.request.MoneyTransferRequest;
import com.mashreq.money.transfer.money_transfer_service.model.response.MoneyTransferResponse;

public interface MoneyTransferService {
    MoneyTransferResponse performMoneyTransfer(MoneyTransferRequest request);
}
