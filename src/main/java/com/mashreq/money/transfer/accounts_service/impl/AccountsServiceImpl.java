package com.mashreq.money.transfer.accounts_service.impl;

import com.mashreq.money.transfer.accounts_service.AccountsService;
import com.mashreq.money.transfer.accounts_service.model.AccountDetails;
import com.mashreq.money.transfer.ports.AccountsServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService {
    private final AccountsServicePort accountsServicePort;
    @Override
    public AccountDetails fetchAccountDetails(String cif, String accountNumber) {
        return accountsServicePort.fetchAccountDetails(cif, accountNumber);
    }
}
