package data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleIDTest {

    @Test
    void testValidVehicleID() {
        VehicleID vehicleID = new VehicleID("ABC12345");
        assertEquals("ABC12345", vehicleID.getId());
    }

    @Test
    void testInvalidVehicleID_NullID() {
        assertThrows(IllegalArgumentException.class, () -> new VehicleID(null),
                "Expected exception for null VehicleID");
    }

    @Test
    void testInvalidVehicleID_EmptyID() {
        assertThrows(IllegalArgumentException.class, () -> new VehicleID(""),
                "Expected exception for empty VehicleID");
    }

    @Test
    void testInvalidVehicleID_InvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> new VehicleID("123"),
                "Expected exception for VehicleID shorter than 5 characters");

        assertThrows(IllegalArgumentException.class, () -> new VehicleID("INVALID_ID_TOO_LONG123"),
                "Expected exception for VehicleID longer than 15 characters");

        assertThrows(IllegalArgumentException.class, () -> new VehicleID("ID@123"),
                "Expected exception for VehicleID with invalid characters");
    }

    @Test
    void testEqualsAndHashCode() {
        VehicleID vehicleID1 = new VehicleID("ABC12345");
        VehicleID vehicleID2 = new VehicleID("ABC12345");
        VehicleID vehicleID3 = new VehicleID("XYZ67890");

        assertEquals(vehicleID1, vehicleID2, "Expected vehicleID1 and vehicleID2 to be equal");
        assertNotEquals(vehicleID1, vehicleID3, "Expected vehicleID1 and vehicleID3 to be different");
        assertEquals(vehicleID1.hashCode(), vehicleID2.hashCode(), "Expected equal hashCodes for vehicleID1 and vehicleID2");
        assertNotEquals(vehicleID1.hashCode(), vehicleID3.hashCode(), "Expected different hashCodes for vehicleID1 and vehicleID3");
    }

    @Test
    void testToString() {
        VehicleID vehicleID = new VehicleID("ABC12345");
        String toStringOutput = vehicleID.toString();
        assertTrue(toStringOutput.contains("ABC12345"), "Expected toString to contain the VehicleID");
        assertTrue(toStringOutput.contains("VehicleID"), "Expected toString to contain class name");
    }

}
