package api.account;

import api.ApiException;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountAction {

    void save(Account account);

    Optional<Account> getById(String accountId);

    void debit(String accountIdToDebit, BigDecimal debitValue) throws ApiException;

    void credit(String accountIdToCredit, BigDecimal creditValue) throws ApiException;
}
