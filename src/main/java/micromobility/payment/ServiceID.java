package micromobility.payment;

public final class ServiceID {
    private final String id;

    // Constructor que acepta String
    public ServiceID(String id) {
        if (id == null || id.isEmpty() || !id.matches("[A-Z0-9]{5,15}")) {
            throw new IllegalArgumentException("Invalid ServiceID format");
        }
        this.id = id;
    }

    // Constructor que acepta int y lo convierte a String
    public ServiceID(int id) {
        this(String.valueOf(id)); // Convierte el int a String y reutiliza la validación del otro constructor
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceID serviceID = (ServiceID) o;
        return id.equals(serviceID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ServiceID{id='" + id + "'}";
    }
}
