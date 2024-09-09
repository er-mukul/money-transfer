package com.mashreq.money.transfer.accounts_service;

import com.mashreq.money.transfer.accounts_service.model.AccountDetails;

public interface AccountsService {
    AccountDetails fetchAccountDetails(String cif, String accountNumber);
}
