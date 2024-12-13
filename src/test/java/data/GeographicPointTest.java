package data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicPointTest {

    @Test
    void testValidGeographicPoint() {
        GeographicPoint point = new GeographicPoint(45.0f, -93.0f);
        assertEquals(45.0f, point.getLatitude());
        assertEquals(-93.0f, point.getLongitude());
    }

    @Test
    void testInvalidLatitude() {
        assertThrows(IllegalArgumentException.class, () -> new GeographicPoint(100.0f, -93.0f));
    }

    @Test
    void testInvalidLongitude() {
        assertThrows(IllegalArgumentException.class, () -> new GeographicPoint(45.0f, -200.0f));
    }

    @Test
    void testEqualsAndHashCode() {
        GeographicPoint point1 = new GeographicPoint(45.0f, -93.0f);
        GeographicPoint point2 = new GeographicPoint(45.0f, -93.0f);
        assertEquals(point1, point2);
        assertEquals(point1.hashCode(), point2.hashCode());
    }

    @Test
    void testToString() {
        GeographicPoint point = new GeographicPoint(45.0f, -93.0f);
        assertTrue(point.toString().contains("45.0"));
        assertTrue(point.toString().contains("-93.0"));
    }

}
