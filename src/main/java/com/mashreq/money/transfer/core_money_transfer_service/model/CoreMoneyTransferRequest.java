package com.mashreq.money.transfer.core_money_transfer_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoreMoneyTransferRequest {
    private DebitCreditData debitLegData;
    private DebitCreditData creditLegData;
    private String reason;
    private String notes;
}
