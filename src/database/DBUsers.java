package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Salesperson;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This abstract class is for retrieving and manipulating the User data in the database and not meant to be instantiated.
 * The class contains one method that returns all data in the form of an ObservableList of User objects.
 * @author Gregory Farrell
 * @version 1.1
 */
public abstract class DBUsers {
    /** This method returns all records in the User table of the database.
     * @return An ObservableList of User objects.
     * */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        User.allUserList.clear();

        try {
            String sqlCommand = "SELECT * FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                int adminKey = rs.getInt("Admin_Key");

                User tempUser= new User(userId, userName, userPassword, adminKey);
                allUsers.add(tempUser);
                User.allUserList.add(tempUser);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return allUsers;
    }

    /** This method is used to insert a new User record into the database.
     * @return An int of the number of affected records
     * @param userName Username as a string.
     * @param password Password as a string.
     * @param adminKey AdminKey as an int.
     * @throws SQLException Throws a SQLException if the SQL commands do not execute properly. */
    public static int insertUser(String userName, String password, int adminKey) throws SQLException {

        String sqlCommand = "INSERT INTO users (User_Name, Password, Admin_Key) VALUES (?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, userName);
        ps.setString(2, password);
        ps.setInt(3, adminKey);

        return ps.executeUpdate();
    }

    /** This method retrieves the UserId for the last inserted record. It is used for creating new salesperson and
     * administrator records immediately after new users are created. */
    public static int getLastInsertedId() {
        int userId = 0;
        try {
            String sqlCommand = "SELECT * FROM users where User_ID = (SELECT LAST_INSERT_ID());";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userId = rs.getInt("User_ID");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return userId;

    }
}
