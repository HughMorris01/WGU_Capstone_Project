package model;

import database.DBContacts;
import database.DBCustomers;
import database.DBUsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** This is the Appointment class for creating objects representing Appointments that exist in the database.
 *  Each Appointment has an appointmentID, title, description, location, contactId, type, start, end, customerId, userId,
 *  and Contact.
 * @author Gregory Farrell
 * @version 1.0
 */
public class Appointment {
    /** Appointment ID as an int. */
    private int appointmentId;
    /** Title as a string. */
    private String title;
    /** Description as a string. */
    private String description;
    /** Location as a string. */
    private String location;
    /** Contact ID as an int. */
    private int contactId;
    /** Type as a string. */
    private String type;
    /** Start as a LocalDateTime. */
    private LocalDateTime start;
    private String startDateString;
    private String startTimeString;
    /** End as a LocalDateTime. */
    private LocalDateTime end;
    private String endTimeString;
    /** Customer ID as an int. */
    private int customerId;
    /** User ID as an int. */
    private int userId;
    /** Contact object instance variable. */
    private Contact contact;
    /** Customer object instance variable. */
    private Customer customer;
    /** User object instance variable. */
    private User user;

    /** Constructor used to create Appointment objects.
     * This constructor is used for creating Appointment objects to move back and forth in the database.
     * @param appointmentId appointmentId as an int.
     * @param start Appointment start time as a LocalDateTime
     * @param end Appointment end time as a LocalDateTime
     * @param title title as a string .
     * @param description description as a string.
     * @param location location as an string.
     * @param type type string.
     * @param customerId customerId as an int.
     * @param userId userId as an int.
     * @param contactId contactId as an int.
     * */
    public Appointment(int appointmentId, LocalDateTime start, LocalDateTime end, String title, String description,
                       String location, String type, int customerId, int userId, int contactId) {
        setAppointmentId(appointmentId);
        setStart(start);
        setStartDateString();
        setStartTimeString();
        setEnd(end);
        setEndTimeString();
        setTitle(title);
        setDescription(description);
        setLocation(location);
        setType(type);
        setCustomerId(customerId);
        setUserId(userId);
        setContactId(contactId);
    }

    /** Sets the appointmentId instance variable.
     * @param appointmentId appointmentId as an int. */
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    /** Sets the title instance variable.
     * @param title title as a string. */
    public void setTitle(String title) { this.title = title; }
    /** Sets the title instance variable.
     * @param description description as a string. */
    public void setDescription(String description) { this.description = description; }
    /** Sets the location instance variable.
     * @param location location as a string. */
    public void setLocation(String location) { this.location = location; }
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
    /** Sets the contact instance variable.
     * @param contact end as a Contact object. */
    public void setContact(Contact contact) { this.contact = contact; }
    /** Sets the user instance variable.
     * @param user user as a User object. */
    public void setUser(User user) { this.user = user; }
    /** Sets the customer instance variable.
     * @param customer customer as a Customer object. */
    public void setCustomer(Customer customer) { this.customer = customer;}
    /** Sets the userId instance variable.
     * @param userId userId as an int. */
    public void setUserId(int userId) {
        this.userId = userId;
        for(User u : DBUsers.getAllUsers()) {
            if(u.getUserId() == userId) {
                setUser(u);
                break;
            }
        }
    }
    /** Sets the contactId instance variable.
     * @param contactId contactId as an int. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
        for(Contact c : DBContacts.getAllContacts()) {
            if(c.getId() == contactId) {
                setContact(c);
                break;
            }
        }
    }
    /** Sets the customerId instance variable.
     * @param customerId customerId as an int. */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
        for(Customer c : DBCustomers.getAllCustomers()) {
            if(c.getCustomerId() == customerId) {
                setCustomer(c);
                break;
            }
        }
    }

    /** Method returns the appointmentId field.
     * @return appointmentId as an int. */
    public int getAppointmentId() { return appointmentId; }
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
    /** Method returns the title field.
     * @return title as a string. */
    public String getTitle() { return title; }
    /** Method returns the description field.
     * @return description as a string. */
    public String getDescription() { return description; }
    /** Method uses the Customer's Division field to return the location field.
     * @return location as a string. */
    public String getLocation() { return this.customer.getDivision().getDivisionName(); }
    /** Method returns the type field.
     * @return type as a string. */
    public String getType() { return type; }
    /** Method returns the customer field.
     * @return customer as a Customer object. */
    public Customer getCustomer() { return customer; }
    /** Method returns the customerId field.
     * @return customerId as an int. */
    public int getCustomerId() { return customerId; }
    /** Method returns the contact field.
     * @return contact as a Contact object. */
    public Contact getContact() { return contact; }
    /** Method returns the contactName field.
     * @return contactName as an string. */
    public String getContactName() { return contact.getContactName(); }
    /** Method returns the contactId field.
     * @return contactId as an int. */
    public int getContactId() { return contactId; }
    /** Method returns the user field.
     * @return user as a User object. */
    public User getUser() { return user; }
    /** Method returns the userId field.
     * @return userId as an int. */
    public int getUserId() { return userId; }
}
