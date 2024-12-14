package micromobility.payment;

import data.UserAccount;
import micromobility.JourneyService;
import java.math.BigDecimal;

public abstract class Payment {
    private final UserAccount user;
    private final JourneyService journey;
    private final BigDecimal amount;

    protected Payment(UserAccount user, JourneyService journey, BigDecimal amount) {
        if (user == null || journey == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid arguments for Payment.");
        }
        this.user = user;
        this.journey = journey;
        this.amount = amount;
    }

    public UserAccount getUser() {
        return user;
    }

    public JourneyService getJourney() {
        return journey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public abstract void processPayment();

    @Override
    public String toString() {
        return "Payment{" +
                "user=" + user.getUsername() + // Simplificamos al username
                ", journeyStartDate=" + (journey.getInitDate() != null ? journey.getInitDate() : "null") + // Solo fecha inicial
                ", amount=" + amount.stripTrailingZeros().toPlainString() + // Formato correcto del BigDecimal
                '}';
    }

}
