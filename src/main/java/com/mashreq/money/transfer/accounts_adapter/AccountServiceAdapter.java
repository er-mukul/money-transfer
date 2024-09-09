package com.mashreq.money.transfer.accounts_adapter;

import com.mashreq.money.transfer.accounts_adapter.client.AccountServiceClient;
import com.mashreq.money.transfer.accounts_adapter.mapper.AccountDetailsResponseMapper;
import com.mashreq.money.transfer.accounts_adapter.model.response.AccountDetailsResponseData;
import com.mashreq.money.transfer.accounts_service.model.AccountDetails;
import com.mashreq.money.transfer.ports.AccountsServicePort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountServiceAdapter implements AccountsServicePort {
    private final AccountServiceClient accountServiceClient;
    private final AccountDetailsResponseMapper accountDetailsResponseMapper;

    @Override
    public AccountDetails fetchAccountDetails(String cif, String accountNumber) {
        AccountDetailsResponseData accountDetails = AccountDetailsResponseData.builder().build();
        try {
            ResponseEntity<AccountDetailsResponseData> accountDetailsResponse = accountServiceClient.fetchAccountDetails(cif, accountNumber);
            if(HttpStatus.OK.value() == accountDetailsResponse.getStatusCode().value()) {
                log.info("Account Details fetched for cif: {}",cif);
                accountDetails = accountDetailsResponse.getBody();
            } else if(HttpStatus.BAD_REQUEST.value() == accountDetailsResponse.getStatusCode().value()) {
                log.error("Bad request error occurred while fetching Account Details for cif: {}", cif);
            } else {
                log.error("Error occurred while fetching account details for cif: {} with HTTP status {}",
                        cif, accountDetailsResponse.getStatusCode());
            }

        } catch (FeignException feignException) {
            log.error("Error occurred while fetching account details for cif: {} with HTTP status {} and message {}",
                    cif, feignException.status(), feignException.getMessage());
        }
        return accountDetailsResponseMapper.map(accountDetails);
    }
}
