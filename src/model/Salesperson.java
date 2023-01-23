package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Salesperson class for creating objects representing Salespersons that exist in the database.
 *  Each Salesperson has an salespersonID number, first name, last name, email address, userID and a regionID. They
 *  also contain fields for tracking the number of appointments that a salesperson has scheduled and completed.
 * @author Gregory Farrell
 * @version 1.1
 */
public class Salesperson extends User {
    /** Static ObservableList field for accessing all Salespersons so that the database only needs to be queried once when
     * the application initially launches.
     * */
    public static ObservableList<Salesperson> allSalespersonsList = FXCollections.observableArrayList();

    /** SalespersonID as an int. */
    private int salespersonId;
    /** First name as a string. */
    private String salespersonFirstName;
    /** Last name as a string. */
    private String salespersonLastName;
    /** Email as a string. */
    private String email;
    // /** UserID as an int. */
    //private int userId;
    /** RegionID as an int. */
    private int regionId;
    /** Region name as a string. */
    private String regionName;
    /** Completed appointments associated with this salesperson as an int. */
    private int completedAppointments;
    /** Scheduled appointments associated with this salesperson as an int. */
    private int scheduledAppointments;
    /** Total appointments associated with this salesperson as an int. */
    private int totalAppointments;
    /** Total customers associated with this salesperson as an int. */
    private int totalCustomers;


    /** Constructor used to create Salesperson objects.
     * This constructor is used for creating Salesperson objects from existing salespersons in the database.
     * @param salespersonId salespersonId as an int.
     * @param salespersonFirstName salespersonFirstName as a string.
     * @param salespersonLastName salespersonLastName as a string.
     * @param email Email as a string.
     * @param userId userId as an int.
     * @param regionId regionId as an int.*/
    public Salesperson(int salespersonId, String salespersonFirstName, String salespersonLastName,
                       String email, int userId, int regionId) {
        super(userId);
        setSalespersonId(salespersonId);
        setSalespersonFirstName(salespersonFirstName);
        setSalespersonLastName(salespersonLastName);
        setEmail(email);
        //setUserId(userId);
        setRegionId(regionId);
        setScheduledAppointments();
        setCompletedAppointments();
        setTotalCustomers();
        setRegionName();
    }

    /** Method sets the salespersonId field.
     * @param salespersonId salespersonId as an int. */
    public void setSalespersonId(int salespersonId) { this.salespersonId = salespersonId; }
    /** Method sets the salespersonFirstName field.
     * @param salespersonFirstName salespersonFirstName as a string. */
    public void setSalespersonFirstName(String salespersonFirstName) { this.salespersonFirstName = salespersonFirstName; }
    /** Method sets the salespersonLastName field.
     * @param salespersonLastName salespersonLastName as a string. */
    public void setSalespersonLastName(String salespersonLastName) { this.salespersonLastName = salespersonLastName; }
    /** Method sets the email field.
     * @param email email as a string. */
    public void setEmail(String email) { this.email = email; }
    /** Method sets the userId field.
     * @param userId userId as an int. */
    //public void setUserId(int userId) { this.userId = userId; }
    /** Method sets the regionId field.
     * @param regionId regionId as an int. */
    public void setRegionId(int regionId) { this.regionId = regionId; }
    /** Loops through the scheduled appointment static observable list to count the number of appointments associated
     * with this salesperson instance*/
    public void setScheduledAppointments() {
        int apptCounter = 0;
        for(Appointment apt : Appointment.allUpcomingAppointmentsList) {
            if(apt.getSalespersonId() == salespersonId) {
                apptCounter += 1;
            }
        }
        this.scheduledAppointments = apptCounter;
    }
    /** Loops through the completed appointment static observable list to count the number of appointments associated
     * with this salesperson instance*/
    public void setCompletedAppointments() {
        int apptCounter = 0;
        for(Appointment apt : Appointment.allCompletedAppointmentsList) {
            if(apt.getSalespersonId() == salespersonId) {
                apptCounter += 1;
            }
        }
        this.completedAppointments = apptCounter;
        this.totalAppointments = scheduledAppointments + completedAppointments;
    }
    /** Loops through the customer static observable list to count the number of customers associated
     * with this salesperson instance*/
    public void setTotalCustomers() {
        int customerCounter = 0;
        for(Customer customer : Customer.allCustomerList) {
            if(customer.getSalespersonId() == salespersonId) {
                customerCounter += 1;
            }
        }
        this.totalCustomers = customerCounter;
    }
    public void setRegionName() {
        for(Region region : Region.allRegionsList) {
            if(region.getRegionId() == regionId) {
                regionName = region.getRegionName();
                return;
            }
        }
    }

    /** Method returns the salespersonId field.
     * @return salespersonId as an int. */
    public int getSalespersonId() { return salespersonId; }
    /** Method returns the salespersonFirstName field.
     * @return salespersonFirstName as a string. */
    public String getSalespersonFirstName() { return salespersonFirstName; }
    /** Method returns the salespersonLastName field.
     * @return salespersonLastName as a string. */
    public String getSalespersonLastName() { return salespersonLastName; }
    /** Method returns the email field.
     * @return email as a string. */
    public String getEmail() { return email; }
    /** Method returns the userId field.
     * @return userId as an int. */
    public int getUserId() { return userId; }
    /** Method returns the regionId field.
     * @return regionId as an int. */
    public int getRegionId() { return regionId; }
    /** Method returns the salesperson's concatenated name to use as table data.
     * @return Last name, first name as a string */
    public String getFullName() { return getSalespersonLastName() + ", " + getSalespersonFirstName(); }
    /** Method returns the scheduledAppointments field.
     * @return scheduledAppointments as an int. */
    public int getScheduledAppointments() { return scheduledAppointments; }
    /** Method returns the completedAppointments field.
     * @return completedAppointments as an int. */
    public int getCompletedAppointments() { return completedAppointments; }
    /** Method returns the totalAppointments field.
     * @return totalAppointments as an int. */
    public int getTotalAppointments() { return totalAppointments; }
    /** Method returns the totalCustomers field.
     * @return totalCustomers as an int. */
    public int getTotalCustomers() { return totalCustomers; }
    /** Method returns the regionName field.
     * @return regionName as a string. */
    public String getRegionName() { return regionName; }

    /** Method overrides the inherited toString() method to return the salespersonFirstName and salespersonLastName fields.
     * @return concatenated names as a string. */
    public String toString() { return this.salespersonFirstName + " " + this.salespersonLastName; }
}
