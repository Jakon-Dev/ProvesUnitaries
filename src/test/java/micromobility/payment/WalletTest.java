package micromobility.payment;

import exceptions.NotEnoughWalletException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void testWalletInitialization() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), wallet.getBalance());
    }

    @Test
    void testInitializationFailsWithNullBalance() {
        assertThrows(IllegalArgumentException.class, () -> new Wallet(null));
    }

    @Test
    void testInitializationFailsWithNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> new Wallet(BigDecimal.valueOf(-10)));
    }

    @Test
    void testDeductFundsSuccessfully() throws NotEnoughWalletException {
        Wallet wallet = new Wallet(BigDecimal.valueOf(100));
        wallet.deduct(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(50), wallet.getBalance());
    }

    @Test
    void testDeductFailsWithInsufficientFunds() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(50));
        assertThrows(NotEnoughWalletException.class, () -> wallet.deduct(BigDecimal.valueOf(100)));
    }

    @Test
    void testDeductFailsWithNegativeAmount() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(50));
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(BigDecimal.valueOf(-10)));
    }

    @Test
    void testDeductFailsWithNullAmount() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(50));
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(null));
    }

    @Test
    void testToString() {
        Wallet wallet = new Wallet(BigDecimal.valueOf(100));
        String expected = "Wallet{balance=100}";
        assertEquals(expected, wallet.toString());
    }
}
