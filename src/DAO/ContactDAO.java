package DAO;

import Model.Contact;
import Model.Customer;
import Model.FLDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class deals with accessing contact information from the database. Queries the contacts table in the database and retrieves
 * the results.*/
public class ContactDAO {

    /** Retrieves contacts from the database. Queries the contacts table in the database and retrieves all contacts.
     * @return all the contacts from the database
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        String sqlSelectAllContacts = "SELECT Contact_ID, Contact_Name, Email FROM client_schedule.contacts";
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), sqlSelectAllContacts);
        ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectAllContacts);
        while(rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contact contact = new Contact(contactId, contactName, email);
            allContacts.add(contact);
        }
        JDBC.closeConnection();
        return allContacts;
    }
}
