package Model;

/** This class is responsible for creating country objects.*/
public class Country {
    private int id;
    private String country;

    /** This is a paramaterized constructor for creating country objects.*/
    public Country(int id, String country) {
        this.id = id;
        this.country = country;
    }

    /** This is the default constructor for creating country objects.*/
    public Country() {

    }

    /**
     * @return the country id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id of the country
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name of the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country sets the country name
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** Overrides the toString method
     * @return the country name
     */
    @Override
    public String toString() {
        return country;
    }
}
