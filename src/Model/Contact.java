package Model;

/** This class allows the creation of contact objects.*/
public class Contact {
    private int id;
    private String name;
    private String email;

    /** This is a paramaterized constructor for contact objects.*/
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** This is the default constructor for contact objects.*/
    public Contact() {

    }

    /**
     * @return the contact id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id of the contact
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the contact name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the contact email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email of the contact
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Overrides the toString method
     * @return the name of the contact
     */
    @Override
    public String toString() {
        return name;
    }

}
