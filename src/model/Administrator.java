package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Administrator extends User {
    /** Static ObservableList field for accessing all Administrators so that the database only needs to be queried once
     * when the application initially launches. */
    public static ObservableList<Administrator> allAdministratorsList = FXCollections.observableArrayList();

    /** AdministratorID as an int. */
    private int administratorId;
    /** First name as a string. */
    private String administratorFirstName;
    /** Last name as a string. */
    private String administratorLastName;
    /** Email as a string. */
    private String email;

    public Administrator(int administratorId, String administratorFirstName, String administratorLastName,
                         String email, int userId) {
        super(userId);
        setAdministratorId(administratorId);
        setAdministratorFirstName(administratorFirstName);
        setAdministratorLastName(administratorLastName);
        setEmail(email);
    }


    // SETTERS
    /** Setter for administratorId instance variable. */
    public void setAdministratorId(int administratorId) { this.administratorId = administratorId; }
    /** Setter for administratorFirstName instance variable. */
    public void setAdministratorFirstName(String administratorFirstName) { this.administratorFirstName = administratorFirstName; }
    /** Setter for administratorLastName instance variable. */
    public void setAdministratorLastName(String administratorLastName) { this.administratorLastName = administratorLastName; }
    /** Setter for email instance variable. */
    public void setEmail(String email) { this.email = email; }

    //GETTERS
    /** Getter for administratorId instance variable. */
    public int getAdministratorId() { return administratorId; }
    /** Getter for administratorFirstName instance variable. */
    public String getAdministratorFirstName() { return administratorFirstName; }
    /** Getter for administratorLastName instance variable. */
    public String getAdministratorLastName() { return administratorLastName; }
    /** Getter for email instance variable. */
    public String getEmail() { return email; }


}
