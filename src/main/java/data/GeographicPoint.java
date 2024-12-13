package data;

final public class GeographicPoint {
    private final float latitude;
    private final float longitude;

    public GeographicPoint(float lat, float lon) {
        if (lat < -90 || lat > 90 || lon < -180 || lon > 180) {
            throw new IllegalArgumentException("Invalid geographic coordinates");
        }
        this.latitude = lat;
        this.longitude = lon;
    }

    public float getLatitude() { return latitude; }

    public float getLongitude() { return longitude; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint gP = (GeographicPoint) o;
        return Float.compare(gP.latitude, latitude) == 0 && Float.compare(gP.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Float.floatToIntBits(latitude) + Float.floatToIntBits(longitude);
    }

    @Override
    public String toString() {
        return "GeographicPoint{latitude=" + latitude + ", longitude=" + longitude + "}";
    }
}