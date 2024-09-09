package com.mashreq.money.transfer.database_adapter.mapper;

import com.mashreq.money.transfer.database_adapter.entities.TransactionEntity;
import com.mashreq.money.transfer.database_adapter.model.TransactionDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class TransactionEntityMapper {
    public TransactionEntity map(TransactionDetails transactionDetails) {
        if(!Objects.nonNull(transactionDetails)) return null;
        else
            return TransactionEntity.builder()
                    .transactionNumber(transactionDetails.getTransactionNumber())
                    .transactionStatus(transactionDetails.getTransactionStatus())
                    .timestamp(transactionDetails.getTimestamp())
                    .build();
    }

    public TransactionDetails mapEntityToModel(TransactionEntity transactionEntity) {
        if(!Objects.nonNull(transactionEntity)) return null;
        else
            return TransactionDetails.builder()
                    .transactionNumber(transactionEntity.getTransactionNumber())
                    .transactionStatus(transactionEntity.getTransactionStatus())
                    .timestamp(transactionEntity.getTimestamp())
                    .build();
    }
}
