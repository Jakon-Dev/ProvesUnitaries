package data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationIDTest {

    @Test
    void testValidStationID() {
        StationID station = new StationID("ABC123");
        assertEquals("ABC123", station.getId());
    }

    @Test
    void testInvalidStationID() {
        assertThrows(IllegalArgumentException.class, () -> new StationID(""));
        assertThrows(IllegalArgumentException.class, () -> new StationID("AB!"));
    }

    @Test
    void testEqualsAndHashCode() {
        StationID station1 = new StationID("ABC123");
        StationID station2 = new StationID("ABC123");
        assertEquals(station1, station2);
        assertEquals(station1.hashCode(), station2.hashCode());
    }

    @Test
    void testToString() {
        StationID station = new StationID("ABC123");
        assertTrue(station.toString().contains("ABC123"));
    }

}