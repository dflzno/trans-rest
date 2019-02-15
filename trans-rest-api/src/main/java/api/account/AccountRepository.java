package api.account;

import api.dataaccess.Repository;
import api.dataaccess.db.Accounts;

import java.util.Optional;

public class AccountRepository implements Repository<Account> {

    @Override
    public Optional<Account> getById(String id) {
        return Accounts.getById(id);
    }

    @Override
    public void save(Account object) {
        Accounts.save(object);
    }
}
