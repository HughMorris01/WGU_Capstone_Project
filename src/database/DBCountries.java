package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is for manipulating the Country table data in the database and not mean to be instantiated.
 * The class contains one method to return all Country data as ObservableList of Country objects.
 * @version 1.0
 */
public class DBCountries {
    /** This method is used to return all of the Country records in the database as an ObserveableList of Customer objects.
     * @return An ObservableList of Country objects.
     * */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountryList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country tempCountry = new Country(countryId, countryName);
                allCountryList.add(tempCountry);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return allCountryList;
    }

}
