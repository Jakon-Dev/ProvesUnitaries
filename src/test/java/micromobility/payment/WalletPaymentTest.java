package micromobility.payment;

import data.UserAccount;
import exceptions.NotEnoughWalletException;
import micromobility.JourneyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WalletPaymentTest {

    private UserAccount user;
    private JourneyService journeyMock;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        user = new UserAccount("user123");
        journeyMock = new JourneyService(null);
        wallet = new Wallet(BigDecimal.valueOf(100));
    }

    @Test
    void testProcessPaymentWithSufficientFunds() throws NotEnoughWalletException {
        WalletPayment payment = new WalletPayment(user, journeyMock, BigDecimal.valueOf(50), wallet);
        payment.processPayment();
        assertEquals(BigDecimal.valueOf(50), wallet.getBalance());
    }

    @Test
    void testProcessPaymentFailsWithInsufficientFunds() {
        WalletPayment payment = new WalletPayment(user, journeyMock, BigDecimal.valueOf(150), wallet);
        assertThrows(NotEnoughWalletException.class, payment::processPayment);
    }

    @Test
    void testInitializationFailsWithNullWallet() {
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(user, journeyMock, BigDecimal.valueOf(50), null));
    }

    @Test
    void testInitializationFailsWithNullAmount() {
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(user, journeyMock, null, wallet));
    }

    @Test
    void testProcessPayment() {
        wallet = new Wallet(BigDecimal.valueOf(50));
        WalletPayment payment = new WalletPayment(user, journeyMock, BigDecimal.valueOf(50), wallet);
        assertDoesNotThrow(payment::processPayment);
        assertEquals(BigDecimal.ZERO, wallet.getBalance());
    }

    @Test
    void testToString() {
        WalletPayment payment = new WalletPayment(user, journeyMock, BigDecimal.valueOf(50), wallet);
        String expectedString = "WalletPayment{" +
                "wallet=" + wallet +
                ", amount=" + BigDecimal.valueOf(50) +
                '}';
        assertEquals(expectedString, payment.toString());
    }
}