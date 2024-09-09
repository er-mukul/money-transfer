package com.mashreq.money.transfer.database_adapter.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transaction_details")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
