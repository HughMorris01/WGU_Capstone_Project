package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class is for manipulating the Customer table data in the database and not mean to be instantiated.
 * The class contains methods to insert, update, delete and return all Customer data as ObservableList of Customer objects.
 * @author Gregory Farrell
 * @version 1.0
 */
public class DBCustomers {

    /** This method is used to insert a new Customer record into the database.
     * @return An int of the number of affected records
     * @param name name as a string.
     * @param address address as a string.
     * @param postalCode postalCode as a string.
     * @param phone phone as a string.
     * @param divisionId divisionId as an int.
     * @throws SQLException Throws a SQLException if the SQL does not execute properly.
     * */
    public static int insertCustomer(String name, String address, String postalCode, String phone, int divisionId) throws SQLException {

        String sqlCommand = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setInt(5, divisionId);
        return ps.executeUpdate();
    }
    /** This method is used to update a Customer record in the database.
     * @return An int of the number of affected records
     * @param customerId as an int.
     * @param updatedName as a string.
     * @param updatedAddress as a string.
     * @param updatedPostalCode as a string.
     * @param updatedPhone as a string.
     * @param updatedDivisionId as an int.
     * @throws SQLException Throws a SQLException if the SQL does not execute properly.
     * */
    public static int updateCustomer(int customerId, String updatedName, String updatedAddress, String updatedPostalCode, String updatedPhone, int updatedDivisionId) throws SQLException {
        String sqlCommand = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, updatedName);
        ps.setString(2, updatedAddress);
        ps.setString(3, updatedPostalCode);
        ps.setString(4, updatedPhone);
        ps.setInt(5, updatedDivisionId);
        ps.setInt(6, customerId);
        return ps.executeUpdate();
    }
    /** This method is used to delete a Customer record in the database.
     * @return An int of the number of affected records
     * @param customerId as an int.
     * @throws SQLException Throws a SQLException if the SQL does not execute properly.
     * */
    public static int deleteCustomer(int customerId) throws SQLException {
        String sqlCommand = "DELETE FROM customers WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setInt(1, customerId);
        return ps.executeUpdate();
    }
    /** This method is used to return all of the Customer records in the database as an ObserveableList of Customer objects.
     * @return An ObservableList of Customer objects.
     * */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                Customer tempCustomer = new Customer(customerId, customerName, customerAddress, customerPostal, customerPhone, divisionId);
                allCustomers.add(tempCustomer);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return allCustomers;
    }
}
