package micromobility.payment;

import data.UserAccount;
import exceptions.NotEnoughWalletException;
import micromobility.JourneyService;

import java.math.BigDecimal;

public class WalletPayment extends Payment {
    private final Wallet wallet;

    public WalletPayment(UserAccount user, JourneyService journey, BigDecimal amount, Wallet wallet) {
        super(user, journey, amount);
        if (wallet == null || wallet.getBalance() == null) {
            throw new IllegalArgumentException("Wallet or wallet balance cannot be null.");
        }
        this.wallet = wallet;
    }

    @Override
    public void processPayment() throws NotEnoughWalletException {
        if (wallet.getBalance().compareTo(getAmount()) < 0) {
            throw new NotEnoughWalletException("Insufficient funds in wallet.");
        }
        wallet.deduct(getAmount());
    }

    @Override
    public String toString() {
        return "WalletPayment{" +
                "wallet=" + wallet +
                ", amount=" + getAmount() +
                '}';
    }
}