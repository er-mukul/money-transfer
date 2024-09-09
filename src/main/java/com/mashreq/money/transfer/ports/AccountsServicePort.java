package com.mashreq.money.transfer.ports;

import com.mashreq.money.transfer.accounts_service.model.AccountDetails;

public interface AccountsServicePort {
    AccountDetails fetchAccountDetails(String cif, String accountNumber);
}
