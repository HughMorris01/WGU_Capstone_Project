package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Customer class for creating objects representing Customers that exist in the database.
 *  Each Customer has an CustomerID, First Name, Last Name, Address, Zip Code, Phone Number, Email, SalespersonID,
 *  StateID and RegionID
 * @author Gregory Farrell
 * @version 1.0
 */
public class Customer {
    /** Static ObservableList for accessing all Customers so that the database only needs to be queried once when
     * the application initially launches.
     * */
    public static ObservableList<Customer> allCustomerList = FXCollections.observableArrayList();

    /** Customer ID as an int. */
    private int customerId;
    /** Customer first name as a string. */
    private String customerFirstName;
    /** Customer last name as a string. */
    private String customerLastName;
    /** Customer address as a string. */
    private String address;
    /** Customer zip code as a string. */
    private String zipCode;
    /** Customer phone number as a string. */
    private String phone;
    /** Customer email as a string. */
    private String email;
    /** SalespersonID as an int. */
    private int salespersonId;
    /** StateID as an int. */
    private int stateId;
    /** RegionID as an int. */
    private int regionId;
    private State customerState;
    private Salesperson customerSalesperson;
    private Region customerRegion;

    /** Constructor used to create Customer objects.
     * This constructor is used for creating Customer objects from existing customers in the database.
     * @param customerId customerId as an int.
     * @param customerFirstName customer first name as a string.
     * @param customerLastName customer last name as a string.
     * @param address address as a string.
     * @param zipCode postalCode as a string .
     * @param phone phone as a string.
     * @param email phone as a string.
     * @param salespersonId divisionId as an int.
     * @param stateId divisionId as an int.
     * @param regionId divisionId as an int.
     * */
    public Customer(int customerId, String customerFirstName, String customerLastName, String address, String zipCode,
                    String phone, String email, int salespersonId, int stateId, int regionId) {
        setCustomerId(customerId);
        setCustomerFirstName(customerFirstName);
        setCustomerLastName(customerLastName);
        setAddress(address);
        setZipCode(zipCode);
        setPhone(phone);
        setEmail(email);
        setSalespersonId(salespersonId);
        setStateId(stateId);
        setRegionId(regionId);

        for(State state : State.allStatesList) {
            if(state.getStateId() == this.stateId) {
                customerState = state;
            }
        }
        for(Region region : Region.allRegionsList) {
            if(region.getRegionId() == this.regionId) {
                customerRegion = region;
            }
        }
        for(Salesperson salesperson : Salesperson.allSalespersonsList) {
            if(salesperson.getSalespersonId() == this.salespersonId) {
                customerSalesperson = salesperson;
            }
        }
    }

    /** Constructor used to create Customer objects.
     * This constructor is used for creating new Customer objects before entering them into the database.
     * @param customerFirstName customer first name as a string.
     * @param customerLastName customer last name as a string.
     * @param address address as a string.
     * @param zipCode postalCode as a string .
     * @param phone phone as a string.
     * @param email phone as a string.
     * @param salespersonId divisionId as an int.
     * @param stateId divisionId as an int.
     * @param regionId divisionId as an int.
     * */
    public Customer(String customerFirstName, String customerLastName, String address, String zipCode,
                    String phone, String email, int salespersonId, int stateId, int regionId) {
        setCustomerFirstName(customerFirstName);
        setCustomerLastName(customerLastName);
        setAddress(address);
        setZipCode(zipCode);
        setPhone(phone);
        setEmail(email);
        setSalespersonId(salespersonId);
        setStateId(stateId);
        setRegionId(regionId);

        for(State state : State.allStatesList) {
            if(state.getStateId() == this.stateId) {
                customerState = state;
                System.out.println("state added");
            }
        }
        for(Region region : Region.allRegionsList) {
            if(region.getRegionId() == this.regionId) {
                customerRegion = region;
                System.out.println("region added");
            }
        }
        for(Salesperson salesperson : Salesperson.allSalespersonsList) {
            if(salesperson.getSalespersonId() == this.salespersonId) {
                customerSalesperson = salesperson;
                System.out.println("salesperson added");
            }
        }
    }

    /** Sets the CustomerId instance variable.
     * @param customerId customerId as an int. */
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    /** Sets the CustomerFirstName instance variable.
     * @param customerFirstName customer first name as a string. */
    public void setCustomerFirstName(String customerFirstName) { this.customerFirstName = customerFirstName; }
    /** Sets the CustomerLastName instance variable.
     * @param customerLastName customer last name as a string. */
    public void setCustomerLastName(String customerLastName) { this.customerLastName = customerLastName; }
    /** Sets the address instance variable.
     * @param address address as a string. */
    public void setAddress(String address) { this.address = address; }
    /** Sets the zipCode instance variable.
     * @param zipCode zipCode as a string. */
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    /** Sets the phone instance variable.
     * @param phone phone as a string. */
    public void setPhone(String phone) { this.phone = phone; }
    /** Sets the email instance variable.
     * @param email email as a string. */
    public void setEmail(String email) { this.email = email; }
    /** Sets the salespersonId instance variable.
     * @param salespersonId salespersonId as an int. */
    public void setSalespersonId(int salespersonId) { this.salespersonId = salespersonId; }
    /** Sets the stateId instance variable.
     * @param stateId stateId as an int. */
    public void setStateId(int stateId) { this.stateId = stateId; }
    /** Sets the regionId instance variable.
     * @param regionId regionId as an int. */
    public void setRegionId(int regionId) { this.regionId = regionId; }


    /** Method returns the customerId field.
     * @return customerId as an int. */
    public int getCustomerId() { return customerId; }
    /** Method returns the customerFirstName field.
     * @return customerFirstName as a string. */
    public String getCustomerFirstName() { return customerFirstName; }
    /** Method returns the customerLastName field.
     * @return customerLastName as a string. */
    public String getCustomerLastName() { return customerLastName; }
    /** Method returns the customer's concatenated first and last names.
     * @return Full name as a string. */
    public String getCustomerFullName() { return getCustomerFirstName() + " " + getCustomerLastName(); }
    /** Method returns the address field.
     * @return address as a string. */
    public String getAddress() { return address; }
    /** Method returns the zipCode field.
     * @return zipCode as a string. */
    public String getZipCode() { return zipCode; }
    /** Method returns the stateAbbreviation based on the stateId.
     * @return stateAbbreviation as a string. */
    public String getStateAbbreviation() {
        String stateAbbreviation = null;
        for(State state : State.allStatesList) {
            if(state.getStateId() == stateId) {
                stateAbbreviation = state.getStateAbbreviation();
                break;
            }
        }
        return stateAbbreviation;
    }
    public String getStateName() {
        String stateName = null;
        for(State state : State.allStatesList) {
            if(state.getStateId() == stateId) {
                stateName = state.getStateName();
                break;
            }
        }
        return stateName;
    }
    /** Method returns the phone field.
     * @return phone as a string. */
    public String getPhone() { return phone; }
    /** Method returns the email field.
     * @return email as a string. */
    public String getEmail() { return email; }
    /** Method returns the salespersonId field.
     * @return salespersonId as an int. */
    public int getSalespersonId() { return salespersonId; }
    /** Method returns the salesperson name based on the salespersonId.
     * @return salesperson name as a string. */
    public String getSalespersonName() {
        String salespersonName = null;
        for(Salesperson salesperson : Salesperson.allSalespersonsList) {
            if(salesperson.getSalespersonId() == salespersonId) {
                salespersonName = salesperson.getFullName();
                break;
            }
        }
        return salespersonName;
    }
    /** Method returns the stateId field.
     * @return stateId as an int. */
    public int getStateId() { return stateId; }
    /** Method returns the regionId field.
     * @return regionId as an int. */
    public int getRegionId() { return regionId; }
    /** Method returns the region name based on the regionID.
     * @return region name as a string. */
    public String getRegionName() { return customerRegion.getRegionName(); }
    public State getCustomerState() { return customerState; }
    public Region getCustomerRegion() { return customerRegion; }
    public Salesperson getCustomerSalesperson() { return customerSalesperson;}

    /** This method is used to traverse the allCustomerList to search for a matching ID or partial matching ID's.
     * @param searchInt an int representing an ID to search for
     * @return an observable list containing the search results
     * */
    public static ObservableList<Customer> searchCustomers(int searchInt) {
        ObservableList<Customer> customersFoundList = FXCollections.observableArrayList();
        for (Customer customer : allCustomerList) {
            if (Integer.toString(customer.getCustomerId()).contains(Integer.toString(searchInt))) {
                customersFoundList.add(customer);
            }
        }
        return customersFoundList;
    }

    /** This method is used to traverse the allCustomerList to search for any partial name matches.
     * @param searchString a string with a name or partial name to search for.
     * @return an observable list containing the search results
     * */
    public static ObservableList<Customer> searchCustomers(String searchString) {
        ObservableList<Customer> customerFoundList = FXCollections.observableArrayList();
        for (Customer customer : allCustomerList) {
            if (customer.getCustomerFullName().contains(searchString)) {
                customerFoundList.add(customer);
            }
        }
        return customerFoundList;
    }


    /** Method overrides the inherited toString() method to return the userName field. */
    @Override
    public String toString() {
        return getCustomerFirstName() + " " + getCustomerLastName();
    }

}