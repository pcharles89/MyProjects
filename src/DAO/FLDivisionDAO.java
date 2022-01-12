package DAO;

import Controller.AddCustomerController;
import Model.FLDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class deals with accessing first level division data from the database. Queries the first level division data in
 * the database and retrieves the results.*/
public class FLDivisionDAO {
    public static ObservableList<FLDivision> getAllDivisions() throws SQLException {
        String sqlSelectAllDivisions = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions";
        ObservableList<FLDivision> allDivisions = FXCollections.observableArrayList();
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(), sqlSelectAllDivisions);
        ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectAllDivisions);
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryId = rs.getInt("Country_Id");
            FLDivision division = new FLDivision(divisionId, divisionName, countryId);
            allDivisions.add(division);
        }
        JDBC.closeConnection();
        return allDivisions;
    }

    /** Retrieves first level divisions from the database. Queries the first level divisions table in the database and
     * retrieves all first level divisions.*/
    public static ObservableList<FLDivision> getFilteredDivisions(String country) throws SQLException {
        ObservableList<FLDivision> filteredDivisions = FXCollections.observableArrayList();
        switch (country) {
            case "U.S": {
                String sqlSelectFilteredDivisions = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID=1";
                JDBC.openConnection();
                Query.setPreparedStatement(JDBC.getConnection(), sqlSelectFilteredDivisions);
                ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectFilteredDivisions);
                while (rs.next()) {
                    int divisionId = rs.getInt("Division_ID");
                    String divisionName = rs.getString("Division");
                    int countryId = rs.getInt("Country_Id");
                    FLDivision division = new FLDivision(divisionId, divisionName, countryId);
                    filteredDivisions.add(division);
                }
                JDBC.closeConnection();
                return filteredDivisions;
            }
            case "UK": {
                String sqlSelectFilteredDivisions = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID=2";
                JDBC.openConnection();
                Query.setPreparedStatement(JDBC.getConnection(), sqlSelectFilteredDivisions);
                ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectFilteredDivisions);
                while (rs.next()) {
                    int divisionId = rs.getInt("Division_ID");
                    String divisionName = rs.getString("Division");
                    int countryId = rs.getInt("Country_Id");
                    FLDivision division = new FLDivision(divisionId, divisionName, countryId);
                    filteredDivisions.add(division);
                }
                JDBC.closeConnection();
                return filteredDivisions;
            }
            case "Canada": {
                String sqlSelectFilteredDivisions = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID=3";
                JDBC.openConnection();
                Query.setPreparedStatement(JDBC.getConnection(), sqlSelectFilteredDivisions);
                ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectFilteredDivisions);
                while (rs.next()) {
                    int divisionId = rs.getInt("Division_ID");
                    String divisionName = rs.getString("Division");
                    int countryId = rs.getInt("Country_Id");
                    FLDivision division = new FLDivision(divisionId, divisionName, countryId);
                    filteredDivisions.add(division);
                }
                JDBC.closeConnection();
                return filteredDivisions;
            }
            default:
                return null;
        }
    }

}





