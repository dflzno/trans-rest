package api.account;

import api.ApiException;
import lombok.Getter;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

@Getter
public class Account {

    private final String accountNumber;
    private BigDecimal balance;

    public Account(final BigDecimal initialBalance) throws ApiException {
        validateValueIsPositive(initialBalance);
        accountNumber = randomUUID().toString();
        this.balance = initialBalance;
    }

    public void credit(final BigDecimal value) throws ApiException {
        validateValueIsPositive(value);
        synchronized (this) {
            balance = balance.add(value);
        }
    }

    public void debit(final BigDecimal value) throws ApiException {
        validateValueIsPositive(value);
        synchronized (this) {
            validateNonNegativeBalance(value);
            balance = balance.subtract(value);
        }
    }

    private static void validateValueIsPositive(final BigDecimal value) throws ApiException {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ApiException("A positive numeric value was expected");
        }
    }

    private void validateNonNegativeBalance(final BigDecimal possibleDebit) throws NotEnoughFundsException {
        if (balance.subtract(possibleDebit).compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughFundsException();
        }
    }
}
