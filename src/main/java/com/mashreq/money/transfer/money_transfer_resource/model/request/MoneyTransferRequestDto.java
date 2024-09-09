package com.mashreq.money.transfer.money_transfer_resource.model.request;

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
public class MoneyTransferRequestDto {
    @NotNull(groups = ValidateMoneyTransferRequest.class)
    @NotEmpty(groups = ValidateMoneyTransferRequest.class)
    @NotBlank(groups = ValidateMoneyTransferRequest.class)
    @Size(min = 10, max = 10, groups = ValidateMoneyTransferRequest.class)
    @Pattern(regexp = REGEX_NUMERIC,groups = ValidateMoneyTransferRequest.class)
    @JsonProperty("cif")
    private String cif;

    @NotNull(groups = ValidateMoneyTransferRequest.class)
    @NotEmpty(groups = ValidateMoneyTransferRequest.class)
    @NotBlank(groups = ValidateMoneyTransferRequest.class)
    @Size(min = 11, max = 11, groups = ValidateMoneyTransferRequest.class)
    @Pattern(regexp = REGEX_NUMERIC,groups = ValidateMoneyTransferRequest.class)
    @JsonProperty("sourceAccount")
    private String sourceAccount;

    @NotNull(groups = ValidateMoneyTransferRequest.class)
    @NotEmpty(groups = ValidateMoneyTransferRequest.class)
    @NotBlank(groups = ValidateMoneyTransferRequest.class)
    @Size(min = 24, max = 24, groups = ValidateMoneyTransferRequest.class)
    @JsonProperty("destinationAccount")
    private String destinationAccount;

    @NotNull(groups = ValidateMoneyTransferRequest.class)
    @NotEmpty(groups = ValidateMoneyTransferRequest.class)
    @NotBlank(groups = ValidateMoneyTransferRequest.class)
    @JsonProperty("amount")
    private String amount;

    @NotNull(groups = ValidateMoneyTransferRequest.class)
    @NotEmpty(groups = ValidateMoneyTransferRequest.class)
    @NotBlank(groups = ValidateMoneyTransferRequest.class)
    @JsonProperty("currency")
    private String currency;

    @NotNull(groups = ValidateMoneyTransferRequest.class)
    @NotEmpty(groups = ValidateMoneyTransferRequest.class)
    @NotBlank(groups = ValidateMoneyTransferRequest.class)
    @JsonProperty("reason")
    private String reason;

    @NotNull(groups = ValidateMoneyTransferRequest.class)
    @NotEmpty(groups = ValidateMoneyTransferRequest.class)
    @NotBlank(groups = ValidateMoneyTransferRequest.class)
    @JsonProperty("notes")
    private String notes;

    public interface ValidateMoneyTransferRequest {

    };
}
