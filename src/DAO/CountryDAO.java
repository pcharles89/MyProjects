package DAO;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {
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
