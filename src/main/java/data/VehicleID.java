package data;

final public class VehicleID {
    private final String id;

    public VehicleID(String id) {
        if (id == null || id.isEmpty() || !id.matches("[A-Z0-9]{5,15}")) {
            throw new IllegalArgumentException("Invalid VehicleID format");
        }
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vehicleID = (VehicleID) o;
        return id.equals(vehicleID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "VehicleID{id='" + id + "'}";
    }
}