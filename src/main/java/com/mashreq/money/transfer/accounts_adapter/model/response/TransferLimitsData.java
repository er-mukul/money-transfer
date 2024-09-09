package com.mashreq.money.transfer.accounts_adapter.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferLimitsData {
    @JsonProperty("monthly")
    private BigDecimal monthly;

    @JsonProperty("daily")
    private BigDecimal daily;

}
