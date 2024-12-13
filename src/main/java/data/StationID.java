package data;

final public class StationID {
    private final String id;

    public StationID(String id) {
        if (id == null || id.isEmpty() || !id.matches("[A-Z0-9]{3,10}")) {
            throw new IllegalArgumentException("Invalid StationID format");
        }
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID stationID = (StationID) o;
        return id.equals(stationID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "StationID{id='" + id + "'}";
    }
}