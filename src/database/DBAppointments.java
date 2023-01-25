package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** This abstract class is for manipulating the Appointment records in the database and not meant to be instantiated.
 * The class contains methods to insert, update, delete and return all Appointment records as an ObservableList of
 * Appointment objects.
 * @author Gregory Farrell
 * @version 1.1
 * */
public abstract class DBAppointments {

    /** This method is used to return all the Appointment records in the database, even if the date has passed, as an
     * ObservableList of Appointment objects.
     * @return An ObservableList of Appointment objects.
     * */
    public static ObservableList<Appointment> getEveryAppointment() {
        Appointment.allAppointmentsList.clear();
        ObservableList<Appointment> everyAppointment = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int salespersonId = rs.getInt("Salesperson_ID");
                int stateId = rs.getInt("State_ID");
                int regionId = rs.getInt("Region_ID");
                Appointment tempAppointment = new Appointment(appointmentId, title, type, start, end, customerId, salespersonId,
                        stateId, regionId);
                everyAppointment.add(tempAppointment);
                Appointment.allAppointmentsList.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        getUpcomingAppointments();
        getCompletedAppointments();
        return everyAppointment;
    }
    /** This method is used to return all the upcoming Appointment records in the database as an ObservableList
     * of Appointment objects.
     * @return An ObservableList of Appointment objects.
     * */
    public static ObservableList<Appointment> getUpcomingAppointments() {
        Appointment.allUpcomingAppointmentsList.clear();
        ObservableList<Appointment> allUpcomingAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start >= ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            LocalDate date = LocalDate.now();
            String dateString = date.toString();
            ps.setString(1, dateString);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int salespersonId = rs.getInt("Salesperson_ID");
                int stateId = rs.getInt("State_ID");
                int regionId = rs.getInt("Region_ID");
                Appointment tempAppointment = new Appointment(appointmentId, title, type, start, end, customerId, salespersonId,
                        stateId, regionId);
                allUpcomingAppointments.add(tempAppointment);
                Appointment.allUpcomingAppointmentsList.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return allUpcomingAppointments;
    }
    /** This method is used to return all the completed Appointment records in the database as an ObservableList
     * of Appointment objects.
     * @return An ObservableList of Appointment objects.
     * */
    public static ObservableList<Appointment> getCompletedAppointments() {
        Appointment.allCompletedAppointmentsList.clear();
        ObservableList<Appointment> allCompletedAppointments = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start < ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            LocalDate date = LocalDate.now();
            String dateString = date.toString();
            ps.setString(1, dateString);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int salespersonId = rs.getInt("Salesperson_ID");
                int stateId = rs.getInt("State_ID");
                int regionId = rs.getInt("Region_ID");
                Appointment tempAppointment = new Appointment(appointmentId, title, type, start, end, customerId, salespersonId,
                        stateId, regionId);
                allCompletedAppointments.add(tempAppointment);
                Appointment.allCompletedAppointmentsList.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return allCompletedAppointments;
    }

    /** This method is used to return all Appointment records in the database that are scheduled in the next 7 days
     * @return An ObservableList of Appointment objects.
     * */
    public static ObservableList<Appointment> getCustomDateRangeList(LocalDate localStartDate, LocalDate localEndDate) {
        ObservableList<Appointment> customDateRangeList = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM appointments WHERE Start >= ? AND Start <= ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            String dateString = localStartDate.toString();
            String dateString2 = localEndDate.toString();
            ps.setString(1, dateString);
            ps.setString(2, dateString2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                Timestamp sts = rs.getTimestamp("Start");
                LocalDateTime start = sts.toLocalDateTime();
                Timestamp ets = rs.getTimestamp("End");
                LocalDateTime end = ets.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int salespersonId = rs.getInt("Salesperson_ID");
                int stateId = rs.getInt("State_ID");
                int regionId = rs.getInt("Region_ID");
                Appointment tempAppointment = new Appointment(appointmentId, title, type, start, end, customerId, salespersonId,
                        stateId, regionId);
                customDateRangeList.add(tempAppointment);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return customDateRangeList;
    }

    /** This method is used to insert a new Appointment record into the database.
     * @param title as a string.
     * @param type as a string
     * @param start as a TimeStamp object
     * @param end as a TimeStamp object.
     * @param customerId as an int.
     * @param salespersonId as an int.
     * @param stateId as an int.
     * @param regionId as an int.
     * @throws SQLException Throws a SQLException if the SQL does not execute properly.
     * */
    public static void insertAppointment(String title, String type, Timestamp start, Timestamp end, int customerId,
                                         int salespersonId, int stateId, int regionId) throws SQLException {
            String sqlCommand = "INSERT INTO appointments (Title, Type, Start, End, Customer_ID, Salesperson_ID, State_ID, Region_ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
            ps.setString(1, title);
            ps.setString(2, type);
            ps.setTimestamp(3, start);
            ps.setTimestamp(4, end);
            ps.setInt(5, customerId);
            ps.setInt(6, salespersonId);
            ps.setInt(7, stateId);
            ps.setInt(8, regionId);
            ps.executeUpdate();
    }

    /** This method is used to update an existing Appointment record into the database.
     * @param title as a string.
     * @param type as a string
     * @param start as a TimeStamp object
     * @param end as a TimeStamp object.
     * @param customerId as an int.
     * @param salespersonId as an int.
     * @param stateId as an int.
     * @param regionId as an int.
     * @param appointmentId as an int.
     * @throws SQLException Throws a SQLException if the SQL does not execute properly.
     * */
    public static void updateAppointment(String title, String type, Timestamp start, Timestamp end, int customerId,
                                         int salespersonId, int stateId, int regionId, int appointmentId) throws SQLException {
        String sqlCommand = "UPDATE appointments SET Title = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, " +
                "Salesperson_ID = ?, State_ID = ?, Region_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setString(1, title);
        ps.setString(2, type);
        ps.setTimestamp(3, start);
        ps.setTimestamp(4, end);
        ps.setInt(5, customerId);
        ps.setInt(6, salespersonId);
        ps.setInt(7, stateId);
        ps.setInt(8, regionId);
        ps.setInt(9, appointmentId);
        ps.executeUpdate();
    }
    /** This method is used to delete existing Appointment record into the database by entering the appointmentId.
     * @param appointmentId as an int.
     * @throws SQLException Throws a SQLException if the SQL does not execute properly.
     * */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String sqlCommand = "DELETE FROM appointments WHERE Appointment_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }
    /** This method is used to delete existing Appointment record into the database by entering the customerId. The method
     * used when associated appointments need to be deleted because a Client record is being deleted.
     * @param customerId as an int.
     * @throws SQLException Throws a SQLException if the SQL does not execute properly.
     * */
    public static void deleteAppointmentByCustomer(int customerId) throws SQLException {
        String sqlCommand = "DELETE FROM appointments WHERE Customer_ID = ? ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        ps.setInt(1, customerId);
        ps.executeUpdate();
    }
}

