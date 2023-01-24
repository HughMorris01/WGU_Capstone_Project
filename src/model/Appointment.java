package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** This is the Appointment class for creating objects representing Appointments that exist in the database.
 *  Each Appointment has an appointmentID, title, type, start, end, customerID, salespersonID, stateID and regionID.
 * @author Gregory Farrell
 * @version 1.1
 */
public class Appointment {
    /** Static ObservableList for accessing all Appointments so that the database only needs to be queried once when
     * the application initially launches.
     * */
    public static ObservableList<Appointment> allAppointmentsList = FXCollections.observableArrayList();
    /** Static ObservableList for accessing all upcoming Appointments so that the database only needs to be queried once when
     * the application initially launches.
     * */
    public static ObservableList<Appointment> allUpcomingAppointmentsList = FXCollections.observableArrayList();
    /** Static ObservableList for accessing all completed Appointments so that the database only needs to be queried once when
     * the application initially launches.
     * */
    public static ObservableList<Appointment> allCompletedAppointmentsList = FXCollections.observableArrayList();

    /** Appointment ID as an int. */
    private int appointmentId;
    /** Title as a string. */
    private String title;
    /** Type as a string. */
    private String type;
    /** Start as a LocalDateTime. */
    private LocalDateTime start;
    /** Start date as a string. */
    private String startDateString;
    /** Start time as a string. */
    private String startTimeString;
    /** End as a LocalDateTime */
    private LocalDateTime end;
    /** End time as a string. */
    private String endTimeString;
    /** CustomerID as an int. */
    private int customerId;
    /** SalespersonID as an int. */
    private int salespersonId;
    /** StateID as an int. */
    private int stateId;
    /** RegionID as an int. */
    private int regionId;
    /** Customer object assigned to the Appointment instance. */
    private Customer appointmentCustomer;
    /** Salesperson object assigned to the Appointment instance. */
    private Salesperson appointmentSalesperson;
    /** State object assigned to the Appointment instance. */
    private State appointmentState;
    /** Region object assigned to the Appointment instance. */
    private Region appointmentRegion;

    /** Constructor used to create Appointment objects.
     * This constructor is used for creating Appointment objects to move back and forth in the database.
     * @param appointmentId appointmentId as an int.
     * @param title title as a string .
     * @param type type string.
     * @param start Appointment start time as a LocalDateTime
     * @param end Appointment end time as a LocalDateTime
     * @param customerId customerId as an int.
     * @param salespersonId salespersonId as an int.
     * @param stateId stateId as an int.
     * @param regionId regionId as an int.
     * */
    public Appointment(int appointmentId, String title, String type, LocalDateTime start, LocalDateTime end,
                       int customerId, int salespersonId, int stateId, int regionId) {
        setAppointmentId(appointmentId);
        setTitle(title);
        setType(type);
        setStart(start);
        setStartDateString();
        setStartTimeString();
        setEnd(end);
        setEndTimeString();
        setCustomerId(customerId);
        setSalespersonId(salespersonId);
        setStateId(stateId);
        setRegionId(regionId);

        setCustomer(customerId);
        setSalesperson(salespersonId);
        setState(stateId);
        setRegion(regionId);
    }

    /** Sets the appointmentId instance variable.
     * @param appointmentId appointmentId as an int. */
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    /** Sets the title instance variable.
     * @param title title as a string. */
    public void setTitle(String title) { this.title = title; }
    /** Sets the type instance variable.
     * @param type type as a string. */
    public void setType(String type) { this.type = type; }
    /** Sets the start instance variable.
     * @param start start as a LocalDateTime. */
    public void setStart(LocalDateTime start) { this.start = start; }
    /** Sets the startDateString instance variable. */
    public void setStartDateString() { DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M-d-yyyy");
        startDateString = dtf.format(this.start); }
    /** Sets the startTimeString instance variable. */
    public void setStartTimeString() { DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
        startTimeString = dtf.format(this.start); }
    /** Sets the end instance variable.
     * @param end end as a LocalDateTime. */
    public void setEnd(LocalDateTime end) { this.end = end; }
    /** Sets the endTimeString instance variable. */
    public void setEndTimeString() { DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
        endTimeString = dtf.format(this.end); }
    /** Sets the customerId instance variable.
     * @param customerId customerId as an int. */
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    /** Sets the salespersonId instance variable.
     * @param salespersonId salespersonId as an int. */
    public void setSalespersonId(int salespersonId) { this.salespersonId = salespersonId; }
    /** Sets the stateId instance variable.
     * @param stateId stateId as an int. */
    public void setStateId(int stateId) { this.stateId = stateId; }
    /** Sets the regionId instance variable.
     * @param regionId regionId as an int. */
    public void setRegionId(int regionId) { this.regionId = regionId; }
    /** Sets the appointmentCustomer instance variable.
     * @param customerId customerId as an int. */
    public void setCustomer(int customerId) {
        for(Customer customer : Customer.allCustomerList) {
            if(customer.getCustomerId() == customerId) {
                appointmentCustomer = customer;
                break;
            }
        }
    }
    /** Sets the appointmentSalesperson instance variable.
     * @param salespersonId salespersonId as an int. */
    public void setSalesperson(int salespersonId) {
        for(Salesperson salesperson : Salesperson.allSalespersonsList) {
            if(salesperson.getSalespersonId() == salespersonId) {
                appointmentSalesperson = salesperson;
                break;
            }
        }
    }
    /** Sets the appointmentState instance variable.
     * @param stateId stateId as an int. */
    public void setState(int stateId) {
        for(State state : State.allStatesList) {
            if(state.getStateId() == stateId) {
                appointmentState = state;
                break;
            }
        }

    }
    /** Sets the appointmentRegion instance variable.
     * @param regionId regionId as an int. */
    public void setRegion(int regionId) {
        for(Region region : Region.allRegionsList) {
            if(region.getRegionId() == regionId) {
                appointmentRegion = region;
                break;
            }
        }
    }


    /** Method returns the appointmentId field.
     * @return appointmentId as an int. */
    public int getAppointmentId() { return appointmentId; }
    /** Method returns the title field.
     * @return title as a string. */
    public String getTitle() { return title; }
    /** Method returns the type field.
     * @return type as a string. */
    public String getType() { return type; }
    /** Method returns the start field.
     * @return start as a LocalDateTime object. */
    public LocalDateTime getStart() { return start; }
    /** Method returns the startDateString field.
     * @return startDateString as a string. */
    public String getStartDateString() {return startDateString; }
    /** Method returns the startTimeString field.
     * @return startTimeString as a string. */
    public String getStartTimeString() { return startTimeString; }
    /** Method returns the end field.
     * @return end as a LocalDateTime object. */
    public LocalDateTime getEnd() { return end; }
    /** Method returns the endTimeString field.
     * @return endTimeString as a string. */
    public String getEndTimeString() { return endTimeString; }
    /** Method returns the appointmentCustomer field.
     * @return appointmentCustomer as a Customer object. */
    public Customer getCustomer() { return appointmentCustomer; }
    /** Method returns the customerId field.
     * @return customerId as an int. */
    public int getCustomerId() { return customerId; }
    /** Method returns the appointment customer's full name for use in appointment tableviews.
     * @return customerFullName as a string */
    public String getCustomerName() { return appointmentCustomer.getCustomerFullName(); }
    /** Method returns the appointmentSalesperson field.
     * @return appointmentSalesperson as a Salesperson object. */
    public Salesperson getSalesperson() { return appointmentSalesperson; }
    /** Method returns the salespersonId field.
     * @return salespersonId as an int. */
    public int getSalespersonId() { return salespersonId; }
    /** Method returns the appointment salesperson's full name for use in appointment tableviews.
     * @return salespersonFullName as a string */
    public String getSalespersonFullName() { return appointmentSalesperson.getFullName(); }
    /** Method returns the appointmentState field.
     * @return appointmentState as a State object. */
    public State getState() { return appointmentState; }
    /** Method returns the stateId field.
     * @return stateId as an int. */
    public int getStateId() { return stateId; }
    /** Method returns the state abbreviation for use in appointment tableviews.
     * @return stateAbbreviation as a string */
    public String getStateAbbreviation() { return appointmentState.getStateAbbreviation(); }
    /** Method returns the appointmentRegion field.
     * @return appointmentRegion as a Region object. */
    public Region getRegion() { return appointmentRegion; }
    /** Method returns the regionId field.
     * @return regionId as an int. */
    public int getRegionId() { return regionId; }
    /** Method returns the region name for use in appointment tableviews.
     * @return regionName as a string */
    public String getRegionName() {
        return appointmentRegion.getRegionName();
    }

    /** This method is used to traverse the allAppointmentList to search for a matching ID or partial matching ID's.
     * @param searchInt an int representing an ID to search for.
     * @return an observable list containing the search results.
     * */
    public static ObservableList<Appointment> searchAppointments(int searchInt) {
        ObservableList<Appointment> appointmentsFoundList = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointmentsList) {
            if (Integer.toString(appointment.getAppointmentId()).contains(Integer.toString(searchInt))) {
                appointmentsFoundList.add(appointment);
            }
            if (Integer.toString(appointment.getCustomerId()).contains(Integer.toString(searchInt))) {
                appointmentsFoundList.add(appointment);
            }
        }
        return appointmentsFoundList;
    }

    /** This method is used to traverse the allAppointmentList to search for any partial name matches.
     * @param searchString a string with a name or partial name to search for.
     * @return an observable list containing the search results
     * */
    public static ObservableList<Appointment> searchAppointments(String searchString) {
        ObservableList<Appointment> appointmentFoundList = FXCollections.observableArrayList();
        for (Appointment appointment : allAppointmentsList) {
            if (appointment.getCustomerName().contains(searchString)) {
                appointmentFoundList.add(appointment);
            }
            if (appointment.getTitle().contains(searchString)) {
                appointmentFoundList.add(appointment);
            }
            if (appointment.getSalespersonFullName().contains(searchString)) {
                appointmentFoundList.add(appointment);
            }
        }
        return appointmentFoundList;
    }

}
