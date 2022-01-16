package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class deals with accessing country information from the database. Queries the countries table in the database and retrieves
 * the results.*/
public class CountryDAO {

    /** Retrieves countries from the database. Queries the countries table in the database and retrieves all countries.
     * @return all the countries from the database
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
    String sqlSelectAllCountries = "SELECT Country_ID, Country FROM client_schedule.countries";
    ObservableList<Country> allCountries = FXCollections.observableArrayList();
        JDBC.openConnection();
        Query.setPreparedStatement(JDBC.getConnection(),sqlSelectAllCountries);
    ResultSet rs = Query.getPreparedStatement().executeQuery(sqlSelectAllCountries);
        while(rs.next())

    {
        int countryId = rs.getInt("Country_ID");
        String countryName = rs.getString("Country");
        Country country = new Country(countryId, countryName);
        allCountries.add(country);
    }
        JDBC.closeConnection();
        return allCountries;
}
}
