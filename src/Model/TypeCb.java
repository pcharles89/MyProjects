package Model;

/** This class is responsible for populating the types combo box in the Reports screen.*/
public class TypeCb {

    private String type;

    /** This is the constructor for the TypeCb class. */
    public TypeCb(String type) {
        this.type = type;
    }

    /** This method overrides the toString method.
     * @return returns the type name
     */
    @Override
    public String toString() {
        return type;
    }
}
