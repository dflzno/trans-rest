package api.db;

import api.account.Account;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountsTest {

    @Test
    public void shouldSaveAndRetrieveAnAccount() {
        // given
        Account a = new Account(new BigDecimal("150"));

        // when
        Accounts.save(a);

        // then
        assertThat(Accounts.getById(a.getAccountNumber())).isPresent();
        assertThat(Accounts.getById(a.getAccountNumber()).get().getBalance()).isEqualTo(new BigDecimal(150));
    }

}