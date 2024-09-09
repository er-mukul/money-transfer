package com.mashreq.money.transfer.database_adapter;

import com.mashreq.money.transfer.database_adapter.client.TransactionJpaClient;
import com.mashreq.money.transfer.database_adapter.entities.TransactionEntity;
import com.mashreq.money.transfer.database_adapter.mapper.TransactionEntityMapper;
import com.mashreq.money.transfer.database_adapter.model.TransactionDetails;
import com.mashreq.money.transfer.ports.DatabasePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DatabaseAdapter implements DatabasePort {
    private final TransactionJpaClient transactionJpaClient;
    private final TransactionEntityMapper transactionEntityMapper;

    @Override
    public TransactionDetails saveTransactionDetails(TransactionDetails transactionDetails) {
        try{
            TransactionEntity transaction = transactionEntityMapper.map(transactionDetails);
            transaction = transactionJpaClient.save(transaction);
            return transactionEntityMapper.mapEntityToModel(transaction);
        } catch (Exception ex) {
            log.error("Error occurred while saving transaction {} in db", transactionDetails);
            return null;
        }

    }
}
