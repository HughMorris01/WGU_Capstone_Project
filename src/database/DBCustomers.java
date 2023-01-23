package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This abstract class is for manipulating the Customer records in the database and not meant to be instantiated.
 * The class contains methods to insert, update, delete and return all Customer records as an ObservableList of
 * Customer objects.
 * @author Gregory Farrell
 * @version 1.1
 */
public abstract class DBCustomers {

    /** This method is used to insert a new Customer record into the database.
     * @return An int of the number of affected records
     * @param firstName first name as a string.
     * @param lastName last name as a string.
     * @param address address as a string.
     * @param zipCode postalCode as a string.
     * @param phone phone as a string.
     * @param email email as a string.
     * @param salespersonId salespersonId as an int.
     * @param stateId stateId as an int.
     * @param regionId regionId as an int.
     * @throws SQLException Throws a SQLException if the SQL commands do not execute properly.
     * */
    public static int insertCustomer(String firstName, String lastName, String address, String zipCode, String phone,
                                     String email, int salespersonId, int stateId, int regionId) throws SQLException {

        String sqlCommand = "INSERT INTO customers (Customer_First_Name, Customer_Last_Name, Address, Zip_Code, Phone," +
                " Email, Salesperson_ID, State_ID, Region_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, address);
        ps.setString(4, zipCode);
        ps.setString(5, phone);
        ps.setString(6, email);
        ps.setInt(7, salespersonId);
        ps.setInt(8, stateId);
        ps.setInt(9, regionId);

        return ps.executeUpdate();
    }
    /** This method is used to update a Customer record in the database.
     * @return An int of the number of affected records
     * @param customerId customerId as an int.
     * @param updatedFirstName first name as a string.
     * @param updatedLastName last name as a string.
     * @param updatedAddress address as a string.
     * @param updatedZipCode postalCode as a string.
     * @param updatedPhone phone as a string.
     * @param updatedEmail email as a string.
     * @param salespersonId salespersonId as an int.
     * @param stateId stateId as an int.
     * @param regionId regionId as an int.
     * @throws SQLException Throws a SQLException if the SQL commands do not execute properly.
     * */
    public static int updateCustomer(int customerId, String updatedFirstName, String updatedLastName, String updatedAddress,
                                     String updatedZipCode, String updatedPhone, String updatedEmail, int salespersonId,
                                     int stateId, int regionId) throws SQLException {
        String sqlCommand = "UPDATE customers SET Customer_First_Name = ?, Customer_Last_Name = ?, Address = ?, Zip_Code = ?, " +
                "Phone = ?, Email = ?, Salesperson_ID = ?, State_ID = ?, Region_ID = ? WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, updatedFirstName);
        ps.setString(2, updatedLastName);
        ps.setString(3, updatedAddress);
        ps.setString(4, updatedZipCode);
        ps.setString(5, updatedPhone);
        ps.setString(6, updatedEmail);
        ps.setInt(7, salespersonId);
        ps.setInt(8, stateId);
        ps.setInt(9, regionId);
        ps.setInt(10, customerId);

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
    /** This method is used to return all the Customer records in the database as an ObservableList of Customer objects.
     * @return An ObservableList of Customer objects.
     * */
    public static ObservableList<Customer> getAllCustomers() {
        Customer.allCustomerList.clear();
        ObservableList<Customer> tempAllCustomers = FXCollections.observableArrayList();

        try {
            String sqlCommand = "SELECT * FROM customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerFirstName = rs.getString("Customer_First_Name");
                String customerLastName = rs.getString("Customer_Last_Name");
                String address = rs.getString("Address");
                String zipCode = rs.getString("Zip_Code");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                int salespersonId = rs.getInt("Salesperson_ID");
                int stateId = rs.getInt("State_ID");
                int regionId = rs.getInt("Region_ID");
                Customer tempCustomer = new Customer(customerId, customerFirstName,  customerLastName, address, zipCode,
                        phone, email, salespersonId, stateId, regionId);
                tempAllCustomers.add(tempCustomer);
                Customer.allCustomerList.add(tempCustomer);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return tempAllCustomers;
    }
}
