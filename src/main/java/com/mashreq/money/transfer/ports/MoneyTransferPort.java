package com.mashreq.money.transfer.ports;

import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferRequest;
import com.mashreq.money.transfer.core_money_transfer_service.model.CoreMoneyTransferResponse;

public interface MoneyTransferPort {
    CoreMoneyTransferResponse performMoneyTransfer(CoreMoneyTransferRequest request);
}
