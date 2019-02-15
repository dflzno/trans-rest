package api.account.integration;

import api.ApiException;
import api.account.Account;
import api.account.AccountAction;
import io.javalin.Context;

import java.util.Optional;

public class AccountRestController {

    private AccountAction accountService;

    public AccountRestController(final AccountAction accountService) {
        this.accountService = accountService;
    }

    public void get(final Context context) {
        Optional<Account> wantedAccount = accountService.getById(context.pathParam("account-id"));
        if (wantedAccount.isPresent()) {
            context.status(200).json(wantedAccount.get());
        } else {
            context.status(404);
        }
    }

    public void post(final Context context) {
        final AccountCreationRequest request = context.bodyAsClass(AccountCreationRequest.class);

        try {
            final Account newAccount = new Account(request.getBalance());
            accountService.save(newAccount);
            context.status(201).json(newAccount);
        } catch (ApiException e) {
            context.status(400).json(e.getMessage());
        }
    }
}
