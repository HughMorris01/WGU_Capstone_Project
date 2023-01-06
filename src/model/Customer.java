package model;

import database.DBFirst_Level_Divisions;

/** This is the Customer class for creating objects representing Customers that exist in the database.
 *  Each Customer has a name, ID, address, postal code, phone number, Division and divisionId.
 * @author Gregory Farrell
 * @version 1.0
 */
public class Customer {
    /** Customer ID as an int. */
    private int customerId;
    /** Customer name as a string. */
    private String customerName;
    /** Customer address as a string. */
    private String address;
    /** Customer postal code as a string. */
    private String postalCode;
    /** Customer phone as a string. */
    private String phone;
    /** Division object associated with each Customer object. */
    private Division division;
    /** Division ID as an int. */
    private int divisionId;

    /** Constructor used to create Customer objects.
     * This constructor is used for creating Customer objects from existing customers in the database.
     * @param customerId customerId as an int.
     * @param customerName customerName as a string.
     * @param address address as a string.
     * @param postalCode postalCode as a string .
     * @param phone phone as a string.
     * @param divisionId divisionId as an int. */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        setCustomerId(customerId);
        setCustomerName(customerName);
        setAddress(address);
        setPostalCode(postalCode);
        setPhone(phone);
        setDivisionId(divisionId);
        for (Division d : DBFirst_Level_Divisions.getAllDivisions()) {
            if (divisionId == d.getDivisionId()) {
                setDivision(d);
                return;
            }
        }
    }

    /** Constructor used to create Customer objects.
     * This constructor is used for creating new Customer objects before entering them into the database.
     * @param customerName customerName as a string.
     * @param address address as a string.
     * @param postalCode postalCode as a string .
     * @param phone phone as a string.
     * @param divisionId divisionId as an int. */
    public Customer(String customerName, String address, String postalCode, String phone, int divisionId) {
        setCustomerName(customerName);
        setAddress(address);
        setPostalCode(postalCode);
        setPhone(phone);
        setDivisionId(divisionId);
        for (Division d : DBFirst_Level_Divisions.getAllDivisions()) {
            if (divisionId == d.getDivisionId()) {
                setDivision(d);
                return;
            }
        }
    }

    /** Sets the CustomerId instance variable.
     * @param customerId customerId as an int. */
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    /** Sets the CustomerName instance variable.
     * @param customerName customerName as a string. */
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    /** Sets the address instance variable.
     * @param address address as a string. */
    public void setAddress(String address) { this.address = address; }
    /** Sets the postalCode instance variable.
     * @param postalCode postalCode as a string. */
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    /** Sets the phone instance variable.
     * @param phone phone as a string. */
    public void setPhone(String phone) { this.phone = phone; }
    /** Sets the divisionId instance variable.
     * @param divisionId divisionId as an int. */
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }
    /** Sets the division instance variable.
     * @param division a Division object is assigned to the instance variable. */
    public void setDivision(Division division) { this.division = division; }

    /** Method returns the customerId field.
     * @return customerId as an int. */
    public int getCustomerId() { return customerId; }
    /** Method returns the customerName field.
     * @return customerName as a string. */
    public String getCustomerName() { return customerName; }
    /** Method returns the address field.
     * @return address as a string. */
    public String getAddress() { return address; }
    /** Method returns the postalCode field.
     * @return postalCode as a string. */
    public String getPostalCode() { return postalCode; }
    /** Method returns the phone field.
     * @return phone as a string. */
    public String getPhone() { return phone; }
    /** Method returns the divisionId field.
     * @return divisionId as an int. */
    public int getDivisionId() { return divisionId; }
    /** Method returns the division field.
     * @return division as a Division. */
    public Division getDivision() { return division; }

    /** Method overrides the inherited toString() method to return the userName field. */
    @Override
    public String toString() {
        String customerToString = getCustomerName() + " : #" + getCustomerId();
        return customerToString;
    }
}