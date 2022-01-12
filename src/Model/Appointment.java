package Model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Objects;

/** This class allows the creation of Appointment objects.*/
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;
    private int contactIndex;

    /** This is a parameterized consstructor to create appointment objects.*/
    public Appointment(int id, String title, String description, String location, String type, String start,
                    String end, int customerId, int userId, int contactId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.setContactName(contactId);
    }

    /** This is a default constructor to create appointment objects.*/
    public Appointment() {

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets the appointment id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title sets the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description sets the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location sets the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type sets the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the start date and time
     */
    public String getStart() {
        return start;
    }

    /**
     * @param start sets the start date and time
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return the end date and time
     */
    public String getEnd() {
        return end;
    }

    /**
     * @param end sets the end date and time
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param custId sets the customer id
     */
    public void setCustomerId(int custId) {
        this.customerId = custId;
    }

    /**
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId sets the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId sets the contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** This method sets the contact name. Determines the name based on the contact id passed as an argument
     * @param contactId the id of the contact whose name is to be changed
     */
    public void setContactName(int contactId) {
        switch(contactId) {
            case 1:
                contactName = "Anika Costa";
                break;
            case 2:
                contactName = "Daniel Garcia";
                break;
            case 3:
                contactName = "Li Lee";
                break;
        }
    }

    /**
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return returns an index of a contact based off of the name
     */
    public int getContactIndex() {
        if(Objects.equals(contactName, "Anika Costa")) {
            return 0;
        }
        else if(Objects.equals(contactName, "Daniel Garcia")) {
            return 1;
        }
        else {
            return 2;
        }
    }

}
