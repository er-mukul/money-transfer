package com.mashreq.money.transfer.money_transfer_resource.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneyTransferResponseFields {
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("transactionNumber")
    private String transactionNumber;

    @JsonProperty("transactionStatus")
    private String transactionStatus;

    @JsonProperty("failureReason")
    private String failureReason;


}
