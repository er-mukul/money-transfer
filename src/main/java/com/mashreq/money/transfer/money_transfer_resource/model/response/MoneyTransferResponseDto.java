package com.mashreq.money.transfer.money_transfer_resource.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.mashreq.money.transfer.commons.AppConstants.REGEX_NUMERIC;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneyTransferResponseDto {
    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private MoneyTransferResponseFields data;


}
