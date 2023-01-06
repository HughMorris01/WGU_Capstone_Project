package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is for manipulating the First_Level_Division table data in the database and not mean to be instantiated.
 * The class contains 4 methods for returning various filtered ObservableLists.
 * @author Gregory Farrell
 * @version 1.0
 */
public class DBFirst_Level_Divisions {

    public static ObservableList<Division> uSDivisionsList = FXCollections.observableArrayList();
    public static ObservableList<Division> uKDivisionsList = FXCollections.observableArrayList();
    public static ObservableList<Division> canadaDivisionsList = FXCollections.observableArrayList();

    /** This method return all of the records in the First_Level_Division table of the database.
     * @return An ObservableList of Division objects.
     * */
    public static ObservableList<Division> getAllDivisions() {
        ObservableList<Division> allDivisionsList = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Division tempDivision;
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                tempDivision = new Division(divisionId, divisionName, countryId);
                allDivisionsList.add(tempDivision);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return allDivisionsList;
    }
    /** This method return all of the US Division records in the First_Level_Division table of the database.
     * @return An ObservableList of Division objects.
     * */
    public static void getUSDivisions() {
        uSDivisionsList.clear();

        try {
            String sqlCommand = "SELECT * FROM first_level_divisions WHERE Country_ID = 1 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Division tempDivision;
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                tempDivision = new Division(divisionId, divisionName, countryId);
                uSDivisionsList.add(tempDivision);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    /** This method return all of the UK Division records in the First_Level_Division table of the database.
     * @return An ObservableList of Division objects.
     * */
    public static void getUKDivisions() {
        uKDivisionsList.clear();

        try {
            String sqlCommand = "SELECT * FROM first_level_divisions WHERE Country_ID = 2 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Division tempDivision;
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                tempDivision = new Division(divisionId, divisionName, countryId);
                uKDivisionsList.add(tempDivision);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    /** This method return all of the Canadian Division records in the First_Level_Division table of the database.
     * @return An ObservableList of Division objects.
     * */
    public static void getCanadaDivisions() {
        canadaDivisionsList.clear();

        try {
            String sqlCommand = "SELECT * FROM first_level_divisions WHERE Country_ID = 3 ";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Division tempDivision;
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                tempDivision = new Division(divisionId, divisionName, countryId);
                canadaDivisionsList.add(tempDivision);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
