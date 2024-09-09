package com.mashreq.money.transfer.accounts_adapter.client;

import com.mashreq.money.transfer.accounts_adapter.model.response.AccountDetailsResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "accountServiceClient",
        url = "${app.services.accountService.url}"
)
public interface AccountServiceClient {
    @GetMapping("/api/v1/accounts/{cif}/{accountNumber}")
    ResponseEntity<AccountDetailsResponseData> fetchAccountDetails(
            @PathVariable("cif") String cif,
            @PathVariable("accountNumber") String accountNumber
    );
}
