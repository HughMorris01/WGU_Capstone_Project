package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Administrator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This abstract class is for manipulating the Administrator records in the database and not meant to be instantiated.
 * The class contains methods to insert and return all Administrator records as an ObservableList of Administrator
 * objects. Right now there is no way to update or delete administrators, once they are created they are final.
 * @author Gregory Farrell
 * @version 1.1
 */
public abstract class DBAdministrators {

    /** This method is used to insert a new Administrator record into the database.
     * @return An int of the number of affected records
     * @param administratorFirstName Administrator first name as a string.
     * @param administratorLastName Administrator last name as a string.
     * @param email email as a string.
     * @param userId userId as an int.
     * @throws SQLException Throws a SQLException if the SQL commands do not execute properly.
     * */
    public static int insertAdministrator(String administratorFirstName, String administratorLastName, String email,
                                        int userId) throws SQLException {

        String sqlCommand = "INSERT INTO administrators (Administrator_First_Name, Administrator_Last_Name," +
                " Email, User_ID) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, administratorFirstName);
        ps.setString(2, administratorLastName);
        ps.setString(3, email);
        ps.setInt(4, userId);

        return ps.executeUpdate();
    }


    /** This method is used to return all the Administrator records in the database as an ObservableList of Administrator
     * objects.
     * @return An ObservableList of Contact objects.
     * */
    public static ObservableList<Administrator> getAllAdministrators() {
        Administrator.allAdministratorsList.clear();
        ObservableList<Administrator> tempAllAdministrators = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM administrators";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int administratorId = rs.getInt("Administrator_ID");
                String administratorFirstName = rs.getString("Administrator_First_Name");
                String administratorLastName = rs.getString("Administrator_Last_Name");
                String email = rs.getString("Email");
                int userId = rs.getInt("User_ID");

                Administrator tempAdministrator= new Administrator(administratorId, administratorFirstName, administratorLastName,
                        email, userId);
                tempAllAdministrators.add(tempAdministrator);
                Administrator.allAdministratorsList.add(tempAdministrator);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return tempAllAdministrators;
    }
}
