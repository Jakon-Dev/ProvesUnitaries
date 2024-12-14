package micromobility.payment;

import exceptions.NotEnoughWalletException;

import java.math.BigDecimal;

public class Wallet {
    private BigDecimal balance;

    public Wallet(BigDecimal initialBalance) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be null or negative.");
        }
        this.balance = initialBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deduct(BigDecimal amount) throws NotEnoughWalletException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (balance.compareTo(amount) < 0) {
            throw new NotEnoughWalletException("Insufficient funds in wallet.");
        }
        balance = balance.subtract(amount);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "balance=" + balance.stripTrailingZeros().toPlainString() +
                '}';
    }

}