package com.mashreq.money.transfer.database_adapter.client;

import com.mashreq.money.transfer.database_adapter.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaClient extends JpaRepository<TransactionEntity, Long> {
}
