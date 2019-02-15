package api.account;

import api.ApiException;
import api.dataaccess.Repository;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Optional;

public class AccountService implements AccountAction {

    private final Repository<Account> repository;

    public AccountService(final Repository<Account> repository) {
        this.repository = repository;
    }

    @Override
    public void save(@NonNull final Account account) {
        repository.save(account);
    }

    @Override
    public Optional<Account> getById(@NonNull final String accountId) {
        return repository.getById(accountId);
    }

    @Override
    public void debit(final String accountIdToDebit, @NonNull final BigDecimal debitValue) throws ApiException {
        final Optional<Account> wantedAccount = getById(accountIdToDebit);
        if (wantedAccount.isPresent()) {
            wantedAccount.get().debit(debitValue);
        } else {
            throw new AccountNotFoundException();
        }
    }

    @Override
    public void credit(final String accountIdToCredit, @NonNull final BigDecimal creditValue) throws ApiException {
        final Optional<Account> wantedAccount = getById(accountIdToCredit);
        if (wantedAccount.isPresent()) {
            wantedAccount.get().credit(creditValue);
        } else {
            throw new AccountNotFoundException();
        }
    }
}
