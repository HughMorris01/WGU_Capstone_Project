package database;

import java.sql.Connection;
import java.sql.DriverManager;

/** Some code for this class was pulled from the C195 Code Repository.
 * This abstract class provides the interface between the Java program and a MySQL database.
 * CONNECTOR VERSION: Connector/J 8.0.25
 * @author Greg Farrell
 * @version 1.0
 */
public abstract class JDBC {
    /** String for database protocol. */
    private static final String protocol = "jdbc";
    /** String for database vendor. */
    private static final String vendor = ":mysql:";
    /** String for database address. */
    private static final String location = "//capstone-db.cget13oy91km.us-east-1.rds.amazonaws.com/";
    /** String for database name. */
    private static final String databaseName = "client_schedule";
    /** String for full URL. */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /** String for driver reference. */
    private static final String driver = "software.aws.rds.jdbc.mysql.Driver";
    /** String for database username. */
    private static final String userName = "hughmorris";
    /** String for database password. */
    final private static String password = "AWSQueen4383";
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
