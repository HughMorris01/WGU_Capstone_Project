package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is for manipulating the Contact table data in the database and not mean to be instantiated.
 * The class contains one method to return all Contact data as an ObservableList of Contact objects.
 * @author Gregory Farrell
 * @version 1.0
 */
public class DBContacts {

    /** This method is used to return all of the Contact records in the database as an ObserveableList of Contact objects.
     * @return An ObservableList of Contact objects.
     * */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact tempContact= new Contact(contactId, contactName, email);
                allContacts.add(tempContact);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return allContacts;
    }
}
