package micromobility.payment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceIDTest {

    @Test
    void testServiceIDInitialization() {
        ServiceID serviceID = new ServiceID("ABC123");
        assertEquals("ABC123", serviceID.getId());
    }

    @Test
    void testServiceIDFailsWithInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceID(null));
        assertThrows(IllegalArgumentException.class, () -> new ServiceID(""));
        assertThrows(IllegalArgumentException.class, () -> new ServiceID("123"));
        assertThrows(IllegalArgumentException.class, () -> new ServiceID("abcde"));
        assertThrows(IllegalArgumentException.class, () -> new ServiceID("ABCDE12345ABCDE12345"));
    }

    @Test
    void testServiceIDInitializationWithInt() {
        ServiceID serviceID = new ServiceID(12345);
        assertEquals("12345", serviceID.getId());
    }

    @Test
    void getId() {
        ServiceID serviceID = new ServiceID("XYZ789");
        assertEquals("XYZ789", serviceID.getId());
    }

    @Test
    void testEquals() {
        ServiceID serviceID1 = new ServiceID("ABC123");
        ServiceID serviceID2 = new ServiceID("ABC123");
        ServiceID serviceID3 = new ServiceID("DEF456");

        assertEquals(serviceID1, serviceID2);
        assertNotEquals(serviceID1, serviceID3);
        assertNotEquals(serviceID1, null);
        assertNotEquals(serviceID1, "ABC123");
    }

    @Test
    void testHashCode() {
        ServiceID serviceID1 = new ServiceID("ABC123");
        ServiceID serviceID2 = new ServiceID("ABC123");

        assertEquals(serviceID1.hashCode(), serviceID2.hashCode());

        ServiceID serviceID3 = new ServiceID("DEF456");
        assertNotEquals(serviceID1.hashCode(), serviceID3.hashCode());
    }

    @Test
    void testToString() {
        ServiceID serviceID = new ServiceID("TEST1");
        assertEquals("ServiceID{id='TEST1'}", serviceID.toString());
    }
}