package tempUI;

public class Earthquake {
    private String id;
    private String UTC_date;
    private double latiude;
    private double longitude;
    private double depth;
    private double magnitude;
    private String region;

    public Earthquake(){
        this.id = null;
        this.UTC_date = null;
        this.latiude = -91.00;
        this.longitude = -181.00;
        this.depth = 0.00;
        this.magnitude = 0.00;
        this.region = null;
    }

    public Earthquake(String id, String UTC_date, double latiude,
                      double longitude, double depth,
                      double magnitude, String region){
        this.id = id;
        this.UTC_date = UTC_date;
        this.latiude = latiude;
        this.longitude = longitude;
        this.depth = depth;
        this.magnitude = magnitude;
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUTC_date() {
        return UTC_date;
    }

    public void setUTC_date(String UTC_date) {
        this.UTC_date = UTC_date;
    }

    public double getLatiude() {
        return latiude;
    }

    public void setLatiude(double latiude) {
        this.latiude = latiude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
