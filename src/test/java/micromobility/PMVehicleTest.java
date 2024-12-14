package micromobility;

import data.GeographicPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PMVehicleTest {

    private PMVehicle vehicle;
    private GeographicPoint initialLocation;

    @BeforeEach
    void setUp() {
        initialLocation = new GeographicPoint( 34.0522F, -118.2437F); // Example: Los Angeles coordinates
        vehicle = new PMVehicle("V123", PMVState.Available, initialLocation);
    }

    @Test
    void getVehicleID() {
        assertEquals("V123", vehicle.getVehicleID(), "The vehicle ID should match the initialized value.");
    }

    @Test
    void getState() {
        assertEquals(PMVState.Available, vehicle.getState(), "The initial state should be 'Available'.");
    }

    @Test
    void getLocation() {
        assertEquals(initialLocation, vehicle.getLocation(), "The location should match the initialized GeographicPoint.");
    }

    @Test
    void setNotAvailb() {
        vehicle.setNotAvailb();
        assertEquals(PMVState.NotAvailable, vehicle.getState(), "The state should be 'NotAvailable' after calling setNotAvailb().");
    }

    @Test
    void setUnderWay() {
        vehicle.setUnderWay();
        assertEquals(PMVState.UnderWay, vehicle.getState(), "The state should be 'UnderWay' after calling setUnderWay().");
    }

    @Test
    void setAvailb() {
        vehicle.setAvailb();
        assertEquals(PMVState.Available, vehicle.getState(), "The state should be 'Available' after calling setAvailb().");
    }

    @Test
    void setLocation() {
        GeographicPoint newLocation = new GeographicPoint(40.7128F, -74.0060F); // Example: New York coordinates
        vehicle.setLocation(newLocation);
        assertEquals(newLocation, vehicle.getLocation(), "The location should update to the new GeographicPoint after calling setLocation().");
    }

    @Test
    void testToString() {
        GeographicPoint initialLocation = new GeographicPoint(41.40338F, 2.17403F);
        PMVehicle vehicle = new PMVehicle("V123", PMVState.Available, initialLocation);

        String expectedString = "PMVehicle{vehicleID='V123', state=Available, location=" + initialLocation + "}";
        assertEquals(expectedString, vehicle.toString(), "The toString() output should match the expected format.");
    }
}