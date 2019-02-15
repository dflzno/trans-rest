package api.account;

import api.ApiException;

public class AccountNotFoundException extends ApiException {
    public AccountNotFoundException() {
        super("Account not found");
    }
}
