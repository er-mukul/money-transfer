package com.mashreq.money.transfer.money_transfer_service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferResponseData {
    private String timestamp;
    private String transactionNumber;
    private String transactionStatus;
    private String failureReason;
}
