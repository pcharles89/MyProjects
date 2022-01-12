package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class deals with accessing user information from the database. Queries the users table in the database and retrieves the
 * results.*/
public class UserDAO {

    /** Retrieves users from the database. Queries the users table in the database and retrieves all users.*/
    public static ObservableList<User> getAllUsers() throws SQLException {
        String sqlSelectAllUsers = "SELECT User_ID, User_Name, Password FROM client_schedule.users";
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), sqlSelectAllUsers);
        ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectAllUsers);
        while(rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user = new User(userId, userName, password);
            allUsers.add(user);
        }
        JDBC.closeConnection();
        return allUsers;
    }
    public static ObservableList<User> getAllFilteredUsers() throws SQLException {
        String sqlSelectAllUsers = "SELECT User_ID, User_Name, Password FROM client_schedule.users";
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), sqlSelectAllUsers);
        ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectAllUsers);
        while(rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            User user = new User(userId, userName, password);
            allUsers.add(user);
        }
        JDBC.closeConnection();
        return allUsers;
    }


}
