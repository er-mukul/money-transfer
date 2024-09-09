package com.mashreq.money.transfer.core_money_transfer_service.model;

import com.mashreq.money.transfer.commons.enums.MoneyTransferStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoreMoneyTransferResponse {
    private MoneyTransferStatus status;
    private String transactionNumber;
}
