package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Salesperson;
import model.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This abstract class is for manipulating the Salesperson records in the database and not meant to be instantiated.
 * The class contains methods to insert, update, delete and return all Salesperson records as an ObservableList of
 * Salesperson objects.
 * @author Gregory Farrell
 * @version 1.1
 */
public abstract class DBSalespersons {

    /** This method is used to insert a new Salesperson record into the database.
     * @return An int of the number of affected records
     * @param salespersonFirstName salesperson first name as a string.
     * @param salespersonLastName salesperson last name as a string.
     * @param email email as a string.
     * @param userId userId as an int.
     * @param regionId regionId as an int.
     * @throws SQLException Throws a SQLException if the SQL commands do not execute properly.
     * */
    public static int insertSalesperson(String salespersonFirstName, String salespersonLastName, String email,
                                     int userId, int regionId) throws SQLException {

        String sqlCommand = "INSERT INTO salespersons (Salesperson_First_Name, Salesperson_Last_Name," +
                " Salesperson_Email, User_ID, Region_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, salespersonFirstName);
        ps.setString(2, salespersonLastName);
        ps.setString(3, email);
        ps.setInt(4, userId);
        ps.setInt(5, regionId);

        return ps.executeUpdate();
    }
    /** This method is used to update an existing Salesperson record in the database.
     * @return An int of the number of affected records
     * @param salespersonId salespersonId as an int.
     * @param updatedFirstName first name as a string.
     * @param updatedLastName last name as a string.
     * @param updatedEmail email as a string.
     * @param updatedRegionId updatedRegionId as an int.
     * @throws SQLException Throws a SQLException if the SQL commands do not execute properly.
     * */
    public static int updateSalesperson(int salespersonId, String updatedFirstName, String updatedLastName,
                                        String updatedEmail, int updatedRegionId) throws SQLException {
        String sqlCommand = "UPDATE salespersons SET Salesperson_First_Name = ?, Salesperson_Last_Name = ?, Salesperson_Email = ?, " +
                "Region_ID = ? WHERE Salesperson_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, updatedFirstName);
        ps.setString(2, updatedLastName);
        ps.setString(3, updatedEmail);
        ps.setInt(4, updatedRegionId);
        ps.setInt(5, salespersonId);

        return ps.executeUpdate();
    }
    /** This method is used to delete a Salesperson record in the database.
     * @return An int of the number of affected records
     * @param salespersonId as an int.
     * @throws SQLException Throws a SQLException if the SQL does not execute properly.
     * */
    public static int deleteSalesperson(int salespersonId) throws SQLException {
        String sqlCommand = "DELETE FROM salespersons WHERE Salesperson_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setInt(1, salespersonId);

        return ps.executeUpdate();
    }

    /** This method is used to return all the Salesperson records in the database as an ObservableList of Salesperson
     * objects.
     * @return An ObservableList of Contact objects.
     * */
    public static ObservableList<Salesperson> getAllSalespersons() {
        Salesperson.allSalespersonsList.clear();
        ObservableList<Salesperson> tempAllSalespersons = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM salespersons";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int salespersonId = rs.getInt("Salesperson_ID");
                String salespersonFirstName = rs.getString("Salesperson_First_Name");
                String salespersonLastName = rs.getString("Salesperson_Last_Name");
                String email = rs.getString("Salesperson_Email");
                int userId = rs.getInt("User_ID");
                int regionId = rs.getInt("Region_ID");

                Salesperson tempSalesperson= new Salesperson(salespersonId, salespersonFirstName, salespersonLastName,
                        email, userId, regionId);
                tempAllSalespersons.add(tempSalesperson);
                Salesperson.allSalespersonsList.add(tempSalesperson);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return tempAllSalespersons;
    }
}
