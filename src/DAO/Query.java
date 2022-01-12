package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Allows a user to execute sql statements more efficiently.*/
public class Query {

    private static PreparedStatement preparedStatement;

    /**
     * @param conn the connection object
     * @param sqlStatement the sql statement the user provides
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        preparedStatement = conn.prepareStatement(sqlStatement);
    }

    /**
     * @return the prepared statement
     */
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }


}
