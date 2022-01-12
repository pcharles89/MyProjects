package Model;

/** This class handles the creation of first level division objects.*/
public class FLDivision {
    private int id;
    private String division;
    private int countryId;

    /** This is a paramaterized constructor to create first level division objects.*/
    public FLDivision(int id, String division, int countryId) {
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    /** This is the default constructor for creating first level division objects.*/
    public FLDivision() {

    }

    /**
     * @return the division id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets the division id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name of the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division sets the division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** Overrides the toString method
     * @return the id and division name together as a string
     */
    @Override
    public String toString() {
        return "[" + id + "] " + division;
    }
}
