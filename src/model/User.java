package model;


/** This is the User class for creating objects representing Users that exist in the database.
 *  Each User has a name, ID and password that are used to authenticate users of the application.
 * @author Gregory Farrell
 * @version 1.0
 */
public class User {
    /** UserId as an int. */
    private int userId;
    /** UserName as a string */
    private String userName;
    /** UserPassword as a string */
    private String userPassword;

    /** Constructor for instantiating User objects.
     * @param userId userId as an int.
     * @param userName userName as a string.
     * @param userPassword userPassword as string.
     * */
    public User(int userId, String userName, String userPassword) {
        setUserId(userId);
        setUserName(userName);
        setUserPassword(userPassword);
    }

    /** Sets the UserId instance variable.
     * @param userId userId as an int. */
    public void setUserId(int userId){ this.userId = userId; }
    /** Sets the userName instance variable.
     * @param userName userName as a string */
    public void setUserName(String userName) { this.userName = userName; }
    /** Sets the userPassword instance variable.
     * @param userPassword userId as an string. */
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    /** Method returns the userId field.
     * @return userId as an int. */
    public int getUserId() { return userId; }
    /** Method returns the userName field.
     * @return userName as a string. */
    public String getUserName() { return userName; }
    /** Method returns the userPassword field.
     * @return userPassword as a string. */
    public String getUserPassword() { return userPassword; }

    /** Method overrides the inherited toString() method to return the userName field. */
    @Override
    public String toString() { return getUserName(); }
}


