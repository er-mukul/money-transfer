package com.mashreq.money.transfer.ports;

import com.mashreq.money.transfer.database_adapter.model.TransactionDetails;

public interface DatabasePort {
    TransactionDetails saveTransactionDetails(TransactionDetails transactionDetails);
}
