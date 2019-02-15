package api.account;

import api.ApiException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceTest {

    private Account testAccount;

    private AccountService testSubject = new AccountService(new AccountRepository());

    @Before
    public void saveAnAccount() throws ApiException {
        testAccount = new Account(new BigDecimal("2000"));
        testSubject.save(testAccount);
    }

    @Test
    public void shouldReturnAnExistingAccount() {
        // when
        final Optional<Account> accountFound = testSubject.getById(testAccount.getAccountNumber());

        // then
        assertThat(accountFound).isPresent();
    }

    @Test
    public void shouldReturnEmptyIfNoAccountWasFound() {
        // when
        final Optional<Account> accountFound = testSubject.getById("nonExistentAccountId");

        // then
        assertThat(accountFound).isEmpty();
    }

    @Test
    public void shouldDebitTheGivenAmountToTheAccount() throws ApiException {
        // when
        testSubject.debit(testAccount.getAccountNumber(), BigDecimal.TEN);

        // then
        assertThat(testAccount.getBalance()).isEqualTo(new BigDecimal("1990"));
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowAnExceptionIfTheAccountToDebitWasNotFound() throws ApiException {
        // when
        testSubject.debit("nonExistentAccountId", BigDecimal.TEN);

        // then
        // addressed in test expectation
    }

    @Test
    public void shouldCreditTheGivenAmountToTheAccount() throws ApiException {
        // when
        testSubject.credit(testAccount.getAccountNumber(), BigDecimal.TEN);

        // then
        assertThat(testAccount.getBalance()).isEqualTo(new BigDecimal("2010"));
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowAnExceptionIfTheAccountToCreditWasNotFound() throws ApiException {
        // when
        testSubject.credit("nonExistentAccountId", BigDecimal.TEN);

        // then
        // addressed in test expectation
    }

}