package com.mashreq.money.transfer.core_money_transfer_adapter.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoreMoneyTransferResponseData {
    @JsonProperty("status")
    private String status;


}
