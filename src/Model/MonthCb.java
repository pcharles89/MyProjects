package Model;

/** This class is responsible for populating the months combo box in the Reports screen.*/
public class MonthCb {

    private String name;

    /** This is the constructor for the MonthCb class. */
    public MonthCb(String name) {
        this.name = name;
    }

    /** This method overrides the toString method.
     * @return returns the month name
     */
    @Override
    public String toString() {
        return name;
    }
}
