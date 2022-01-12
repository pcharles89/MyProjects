package Model;

/** This class allows the creation of User objects.*/
public class User {
    private int id;
    private String userName;
    private String password;

    /** This is a paramaterized constructor for the User class.*/
    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /** This is the default constructor for the User class.*/
    public User() {

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName sets the username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password sets the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Overrides the toString method
     * @return the user id value in a string
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
