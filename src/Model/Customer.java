package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    public static ObservableList<Customer> getAllCustomers() {
       return allCustomers;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String customerName) {
        name = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String customerAddress) {
        address = customerAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
