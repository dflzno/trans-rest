package api.dataaccess.db;

import api.account.Account;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public final class Accounts {

    private static final Map<String, Account> accounts = new HashMap<>();

    private Accounts() {
    }

    public static void save(@NonNull final Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public static Optional<Account> getById(@NonNull final String accountId) {
        return ofNullable(accounts.get(accountId));
    }

}
