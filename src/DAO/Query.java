package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {

    private static PreparedStatement preparedStatement;
    private static Statement statement;
    public static final String sqlSelectAllCustomers = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone" +
            "Division_Id FROM client_schedule.customers";

    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }

    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public static void setStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }
    public static Statement getStatement() {
        return statement;
    }


}
