package com.mashreq.money.transfer.core_money_transfer_service;

import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferRequest;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferResponse;

public interface CoreMoneyTransferService {
    CoreMoneyTransferResponse performMoneyTransfer(CoreMoneyTransferRequest request);
}
