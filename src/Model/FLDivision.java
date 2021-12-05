package Model;

public class FLDivision {
    private int id;
    private String division;
    private int countryId;

    public FLDivision(int id, String division, int countryId) {
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    public FLDivision() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + division;
    }
}
