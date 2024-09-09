package com.mashreq.money.transfer.accounts_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferLimits {
    private BigDecimal monthly;
    private BigDecimal daily;

}
