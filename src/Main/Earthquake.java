package Main;

public class Earthquake {
    private String id;
    private String UTC_date;
    private double latiude;
    private double longitude;
    private double depth;
    private double magnitude;
    private String region;

    /**
     *
     */
    public Earthquake(){
        this.id = null;
        this.UTC_date = null;
        this.latiude = -91.00;
        this.longitude = -181.00;
        this.depth = 0.00;
        this.magnitude = 0.00;
        this.region = null;
    }

    /**
     *
     * @param id
     * @param UTC_date
     * @param latiude
     * @param longitude
     * @param depth
     * @param magnitude
     * @param region
     */
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

    /**
     *
     * @return content of rows of the table
     */
    @Override
    public String toString(){
        return id+" "+UTC_date+" "+latiude+" "+longitude+" "+depth+" "+magnitude+" "+region;
    }

    public String getId() {
        return id;
    }

    public String getUTC_date() {
        return UTC_date;
    }

    public double getLatiude() {
        return latiude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDepth() {
        return depth;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getRegion() {
        return region;
    }

}
