package DAO;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

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
}
