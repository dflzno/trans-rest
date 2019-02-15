package api.account;

import lombok.Getter;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

@Getter
public class Account {

    private final String accountNumber;
    private BigDecimal balance;

    public Account(final BigDecimal initialBalance) {
        accountNumber = randomUUID().toString();
        this.balance = initialBalance;
    }

    public void credit(final BigDecimal value) {
        validateValue(value);
        synchronized (this) {
            balance = balance.add(value);
        }
    }

    public void debit(final BigDecimal value) throws NotEnoughFundsException {
        validateValue(value);
        synchronized (this) {
            validateNonNegativeBalance(value);
            balance = balance.subtract(value);
        }
    }

    private static void validateValue(final BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNonNegativeBalance(final BigDecimal possibleDebit) throws NotEnoughFundsException {
        if (balance.subtract(possibleDebit).compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughFundsException();
        }
    }
}
