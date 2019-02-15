package api.account;

import api.ApiException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    public void shouldGenerateAnAccountIdentifier() throws ApiException {
        // given
        BigDecimal initialBalance = BigDecimal.ZERO;

        // when
        Account a = new Account(initialBalance);

        // then
        assertThat(a.getAccountNumber()).isNotBlank();
    }

    @Test(expected = ApiException.class)
    public void shouldNotAllowToCreateAnAccountWithNegativeBalance() throws ApiException {
        // given
        BigDecimal initialBalance = new BigDecimal("-100");

        // when
        Account a = new Account(initialBalance);

        // then
        // addressed in test expectation
    }

    @Test
    public void shouldSuccessfullyCreditTheAccount() throws ApiException {
        // given
        Account a = new Account(new BigDecimal(1000));

        // when
        a.credit(new BigDecimal("500"));

        // then
        assertThat(a.getBalance()).isEqualTo(new BigDecimal(1500));
    }

    @Test(expected = ApiException.class)
    public void shouldNotAllowCreditingNegativeValues() throws ApiException {
        // given
        Account a = new Account(BigDecimal.ZERO);

        // when
        a.credit(new BigDecimal("-200"));

        // then
        // addressed in test expectation
    }

    @Test
    public void shouldSuccessfullyDebitFromTheAccount() throws ApiException {
        // given
        Account a = new Account(new BigDecimal("1000"));

        // when
        a.debit(new BigDecimal("800"));

        // then
        assertThat(a.getBalance()).isEqualTo(new BigDecimal("200"));
    }

    @Test(expected = ApiException.class)
    public void shouldNotAllowDebitingNegativeValues() throws ApiException {
        // given
        Account a = new Account(BigDecimal.ZERO);

        // when
        a.debit(new BigDecimal("-200"));

        // then
        // addressed in test expectation
    }

    @Test(expected = NotEnoughFundsException.class)
    public void shouldNotAllowToLeaveANegativeBalance() throws ApiException {
        // given
        Account a = new Account(new BigDecimal(100));

        // when
        a.debit(new BigDecimal("250"));

        // then
        // addressed in test expectation
    }

}