package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class is responsible for the creation of customer objects.*/
public class Customer {
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    /** This is a paramaterized constructor to create customer objects.*/
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /** This is the default constructor for creating customer objects.*/
    public Customer() {

    }

    public static ObservableList<Customer> getAllCustomers() {
       return allCustomers;
    }

    /**
     * @return the customer phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone sets the customer phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the customer id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id of the customer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the customer name
     */
    public String getName() {
        return name;
    }

    /**
     * @param customerName sets the customer name
     */
    public void setName(String customerName) {
        name = customerName;
    }

    /**
     * @return the customer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param customerAddress sets the customer address
     */
    public void setAddress(String customerAddress) {
        address = customerAddress;
    }

    /**
     * @return the customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode sets the customer postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the customer division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @param divisionId of the customer
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** Overrides the toString method
     * @return the customer id as a string
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
