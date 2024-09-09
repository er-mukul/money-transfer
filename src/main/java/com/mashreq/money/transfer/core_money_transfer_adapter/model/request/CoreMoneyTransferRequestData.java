package com.mashreq.money.transfer.core_money_transfer_adapter.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoreMoneyTransferRequestData {
    @JsonProperty("debit-leg")
    private DebitCreditRequestData debitLegData;

    @JsonProperty("credit-leg")
    private DebitCreditRequestData creditLegData;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("transactionNumber")
    private String transactionNumber;

}
