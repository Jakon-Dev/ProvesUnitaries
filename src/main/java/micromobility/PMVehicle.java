package micromobility;

import data.GeographicPoint;

public class PMVehicle {
    private String vehicleID; // Asumiendo que puede ser un identificador único
    private PMVState state;
    private GeographicPoint location;

    public PMVehicle(String vehicleID, PMVState state, GeographicPoint location) {
        this.vehicleID = vehicleID;
        this.state = state;
        this.location = location;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public PMVState getState() {
        return state;
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public void setNotAvailb() {
        this.state = PMVState.NotAvailable;
    }

    public void setUnderWay() {
        this.state = PMVState.UnderWay;
    }

    public void setAvailb() {
        this.state = PMVState.Available;
    }

    public void setLocation(GeographicPoint gP) {
        this.location = gP;
    }

    @Override
    public String toString() {
        return "PMVehicle{" +
                "vehicleID='" + vehicleID + '\'' +
                ", state=" + state +
                ", location=" + location +
                '}';
    }
}
