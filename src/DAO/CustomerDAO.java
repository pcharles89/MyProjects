package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class deals with accessing customer information from the database. Queries the customers table in the database and retrieves
 * the results.*/
public class CustomerDAO {

    /** Retrieves customers from the database. Queries the customers table in the database and retrieves all customers.*/
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String sqlSelectAllCustomers = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                "Division_Id FROM client_schedule.customers";
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), sqlSelectAllCustomers);
        ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectAllCustomers);
        while(rs.next()) {
            int custId = rs.getInt("Customer_ID");
            String custName = rs.getString("Customer_Name");
            String custAddress = rs.getString("Address");
            String custPostalCode = rs.getString("Postal_Code");
            String custPhone = rs.getString("Phone");
            int custDivisionId = rs.getInt("Division_ID");
            Customer customer = new Customer(custId, custName, custAddress, custPostalCode, custPhone, custDivisionId);
            allCustomers.add(customer);
        }
        JDBC.closeConnection();
        return allCustomers;
    }

    /** Creates a new customer in the database. Inserts the new customer into the customers table in the database using
     * the arguments provided (which correspond to the tables fields).*/
    public static void createCustomer(int custId, String custName, String address, String pCode, String phone, int divId)
            throws SQLException {
        String insertStatement = "INSERT INTO client_schedule.customers(Customer_ID, Customer_Name, Address, Postal_Code," +
                "Phone, Division_ID) VALUES(?,?,?,?,?,?)";
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement ps = Query.getPreparedStatement();
        ps.setInt(1, custId);
        ps.setString(2, custName);
        ps.setString(3, address);
        ps.setString(4, pCode);
        ps.setString(5, phone);
        ps.setInt(6, divId);

        ps.executeUpdate();
    }

    /** Updates a customer in the database.
     * @param index the customer id of the appointment being updated
     * @param customer the new customer after changes have been made
     */
    public static void updateCustomer(int index, Customer customer) throws SQLException {
        Customer searchedCustomer = CustomerDAO.lookUpCustomer(index);
        CustomerDAO.deleteCustomer(searchedCustomer);
        CustomerDAO.createCustomer(customer.getId(), customer.getName(), customer.getAddress(), customer.getPostalCode(),
                customer.getPhone(), customer.getDivisionId());
    }

    /** Looks up the customer by id. Used in conjunction with the updateCustomer method to update a customer.
     * @param customerID the customer id of the appointment that is to be updated
     * @return returns the customer that is going to be updated
     */
    public static Customer lookUpCustomer(int customerID) throws SQLException {
        for(Customer customer: CustomerDAO.getAllCustomers()) {
            if(customer.getId() == customerID) {
                return customer;
            }
        }
        return null;
    }

    /** Deletes a customer from the database. Queries the customers table in the database and then deletes the customer from it.
     * @param customer the customer to be deleted
     */
    public static void deleteCustomer(Customer customer) throws SQLException {
        String deleteStatement = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), deleteStatement);
        PreparedStatement ps = Query.getPreparedStatement();
        ps.setInt(1, customer.getId());
        ps.executeUpdate();
    }
}
