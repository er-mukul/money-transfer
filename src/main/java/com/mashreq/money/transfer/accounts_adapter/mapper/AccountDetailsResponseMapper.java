package com.mashreq.money.transfer.accounts_adapter.mapper;

import com.mashreq.money.transfer.accounts_adapter.model.response.AccountDetailsResponseData;
import com.mashreq.money.transfer.accounts_service.model.AccountDetails;
import com.mashreq.money.transfer.accounts_service.model.TransferLimits;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class AccountDetailsResponseMapper {
    public AccountDetails map(AccountDetailsResponseData response) {
        if(!Objects.nonNull(response)) return null;
        else
            return AccountDetails.builder()
                    .balance(response.getBalance())
                    .currency(response.getCurrency())
                    .limits(response.getLimits() != null ? TransferLimits.builder()
                            .daily(response.getLimits().getDaily())
                            .monthly(response.getLimits().getMonthly())
                            .build() : null)
                    .build();
    }
}
