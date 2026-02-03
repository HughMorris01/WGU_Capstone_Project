package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This abstract class provides the interface between the Java program and a MySQL database.
 * UPDATED: Configured for Localhost (Standard MySQL)
 * @author Greg Farrell
 * @version 1.2
 */
public abstract class JDBC {
    /** String for database protocol. */
    private static final String protocol = "jdbc";
    /** String for database vendor. */
    private static final String vendor = ":mysql:";
    
    /** String for database address. */
    // Set to localhost for local testing/grading
    private static final String location = "//localhost:3306/";
    
    /** String for database name. */
    private static final String databaseName = "client_schedule";
    
    /** String for full URL. */
    // Added allowPublicKeyRetrieval and useSSL to prevent common local connection errors
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    
    /** String for driver reference. */
    // Switched to standard MySQL driver for easier local deployment
    private static final String driver = "com.mysql.cj.jdbc.Driver"; 
    
    /** String for database username. */
    private static final String userName = "root"; 
    
    /** String for database password. */
    // TODO: Update this to match your local MySQL password!
    final private static String password = "root"; 
    
    /** Connection interface object. */
    private static Connection connection;

    /** Static method uses the driver, database url and login variables to establish a connection with the database. */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Static method that returns the Connection object created by openConnection(). */
    public static Connection getConnection() { return connection; }

    /** Static method that closes the connection established by openConnection(). */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection successfully closed");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
