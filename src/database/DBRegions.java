package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Region;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is for accessing the Regions table data in the database and not meant to be instantiated.
 * The class contains one method to return all region data as ObservableList of Region objects.
 * @version 1.1
 */
public class DBRegions {
    /** This method is used to return all the Region records in the database as an ObservableList of Region objects.
     * @return An ObservableList of Region objects.
     * */
    public static ObservableList<Region> getAllRegions() {
        ObservableList<Region> allRegionList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM regions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int regionId = rs.getInt("Region_ID");
                String regionName = rs.getString("Region_Name");

                Region tempRegion = new Region(regionId, regionName);
                allRegionList.add(tempRegion);
                Region.allRegionsList.add(tempRegion);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return allRegionList;
    }
}