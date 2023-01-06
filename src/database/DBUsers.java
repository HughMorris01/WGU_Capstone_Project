package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is for manipulating the User data in the database and not meant to be instantiated. The class contains
 * one method that returns all data in the form of an ObservableList of User objects.
 * @author Gregory Farrell
 * @version 1.0
 */
public class DBUsers {
    /** This method returns all records in the User table of the database.
     * @return An ObservableList of User objects.
     * */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");

                User tempUser= new User(userId, userName, userPassword);
                allUsers.add(tempUser);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return allUsers;
    }
}
