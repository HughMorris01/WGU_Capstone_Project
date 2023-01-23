package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the User class for creating objects representing Users that exist in the database.
 *  Each User has a userID, userName and a password that are used to authenticate users of the application. There will
 *  be one user in the database created with an admin key that allows the user to view global database data and also edit
 *  Salesperson table.
 * @author Gregory Farrell
 * @version 1.1
 */
public class User {
    /** Static ObservableList for accessing all Users so that the database only needs to be queried once when
     * the application initially launches.
     * */
    public static ObservableList<User> allUserList = FXCollections.observableArrayList();

    /** UserId as an int. */
    protected int userId;
    /** UserName as a string */
    private String userName;
    /** UserPassword as a string */
    private String userPassword;
    /** AdminKey as an int. Differentiates between Salesperson users and admin users */
    private int adminKey;

    /** Constructor for instantiating User objects.
     * @param userId userId as an int.
     * @param userName userName as a string.
     * @param userPassword userPassword as string.
     * */
    public User(int userId, String userName, String userPassword, int adminKey) {
        setUserId(userId);
        setUserName(userName);
        setUserPassword(userPassword);
        setAdminKey(adminKey);
    }

    public User(int userId) {
        setUserId(userId);
        for(User user : User.allUserList){
            if(user.getUserId() == userId){
                this.setUserName(user.getUserName());
                this.setUserPassword(user.getUserPassword());
            }
        }
    }

    /** Sets the UserId instance variable.
     * @param userId userId as an int. */
    public void setUserId(int userId){ this.userId = userId; }
    /** Sets the userName instance variable.
     * @param userName userName as a string */
    public void setUserName(String userName) { this.userName = userName; }
    /** Sets the userPassword instance variable.
     * @param userPassword userId as a string. */
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }
    /** Sets the adminKey instance variable.
     * @param adminKey adminKey as an int. */
    public void setAdminKey(int adminKey) { this.adminKey = adminKey; }

    /** Method returns the userId field.
     * @return userId as an int. */
    public int getUserId() { return userId; }
    /** Method returns the userName field.
     * @return userName as a string. */
    public String getUserName() { return userName; }
    /** Method returns the userPassword field.
     * @return userPassword as a string. */
    public String getUserPassword() { return userPassword; }
    /** Method returns the adminKey field.
     * @return adminKey as an int. */
    public int getAdminKey() { return adminKey; }

    /** Method overrides the inherited toString() method to return the userName field. */
    @Override
    public String toString() { return getUserName(); }
}


