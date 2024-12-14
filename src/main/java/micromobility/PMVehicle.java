package micromobility;

import data.GeographicPoint;

public class PMVehicle {
    private String vehicleID; // Asumiendo que puede ser un identificador único

    private GeographicPoint location;

    public PMVehicle(String vehicleID, GeographicPoint location) {
        this.vehicleID = vehicleID;

        this.location = location;
    }

    public String getVehicleID() {
        return vehicleID;
    }



    public GeographicPoint getLocation() {
        return location;
    }



    public void setLocation(GeographicPoint gP) {
        this.location = gP;
    }

    @Override
    public String toString() {
        return "PMVehicle{" +
                "vehicleID='" + vehicleID + '\'' +
                ", location=" + location +
                '}';
    }
}