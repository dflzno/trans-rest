package api.account;

import api.ApiException;

public class NotEnoughFundsException extends ApiException {

    public NotEnoughFundsException() {
        super("Not enough funds");
    }
}
