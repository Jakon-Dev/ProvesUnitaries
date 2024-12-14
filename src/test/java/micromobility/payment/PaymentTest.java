package micromobility.payment;

import data.UserAccount;
import micromobility.JourneyService;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    static class TestPayment extends Payment {
        public TestPayment(UserAccount user, JourneyService journey, BigDecimal amount) {
            super(user, journey, amount);
        }

        @Override
        public void processPayment() {
            // Implementación vacía para pruebas
        }
    }

    @Test
    void testPaymentInitialization() {
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(null);
        Payment payment = new TestPayment(user, journey, BigDecimal.valueOf(100));

        assertEquals(user, payment.getUser());
        assertEquals(journey, payment.getJourney());
        assertEquals(BigDecimal.valueOf(100), payment.getAmount());
    }

    @Test
    void testPaymentInitializationFailsWithInvalidAmount() {
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(null);

        assertThrows(IllegalArgumentException.class, () -> new TestPayment(user, journey, BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class, () -> new TestPayment(user, journey, null));
    }

    @Test
    void getUser() {
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(null);
        Payment payment = new TestPayment(user, journey, BigDecimal.valueOf(100));

        assertEquals(user, payment.getUser());
    }

    @Test
    void getJourney() {
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(null);
        Payment payment = new TestPayment(user, journey, BigDecimal.valueOf(100));

        assertEquals(journey, payment.getJourney());
    }

    @Test
    void getAmount() {
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(null);
        Payment payment = new TestPayment(user, journey, BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(100), payment.getAmount());
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        UserAccount user = new UserAccount("user123");
        JourneyService journey = new JourneyService(now); // Simulación de JourneyService
        Payment payment = new TestPayment(user, journey, BigDecimal.valueOf(100));

        // Formateamos 'now' al formato esperado
        String expected = "Payment{user=user123, journeyStartDate=" + now.toString() + ", amount=100}";
        assertEquals(expected, payment.toString());
    }

}
