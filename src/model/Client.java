package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Client class for creating objects representing Clients that exist in the database.
 *  Each Client has an ClientID, First Name, Last Name, Address, Zip Code, Phone Number, Email, SalespersonID,
 *  StateID and RegionID
 * @author Gregory Farrell
 * @version 1.1
 */
public class Client {
    /** Static ObservableList for accessing all Clients so that the database only needs to be queried once when
     * the application initially launches. */
    public static ObservableList<Client> allClientList = FXCollections.observableArrayList();

    /** Client ID as an int. */
    private int clientId;
    /** Client first name as a string. */
    private String clientFirstName;
    /** Client last name as a string. */
    private String clientLastName;
    /** Client address as a string. */
    private String address;
    /** Client zip code as a string. */
    private String zipCode;
    /** Client phone number as a string. */
    private String phone;
    /** Client email as a string. */
    private String email;
    /** SalespersonID as an int. */
    private int salespersonId;
    /** StateID as an int. */
    private int stateId;
    /** RegionID as an int. */
    private int regionId;
    /** State object associated with the Client instance */
    private State clientState;
    /** Salesperson object associated with the Client instance */
    private Salesperson clientSalesperson;
    /** Region object associated with the Client instance */
    private Region clientRegion;

    /** Constructor used to create Client objects.
     * This constructor is used for creating Client objects from existing customers in the database.
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
    public Client(int customerId, String customerFirstName, String customerLastName, String address, String zipCode,
                  String phone, String email, int salespersonId, int stateId, int regionId) {
        setClientId(customerId);
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
                clientState = state;
            }
        }
        for(Region region : Region.allRegionsList) {
            if(region.getRegionId() == this.regionId) {
                clientRegion = region;
            }
        }
        for(Salesperson salesperson : Salesperson.allSalespersonsList) {
            if(salesperson.getSalespersonId() == this.salespersonId) {
                clientSalesperson = salesperson;
            }
        }
    }

    /** Constructor used to create Client objects.
     * This constructor is used for creating new Client objects before entering them into the database.
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
    public Client(String customerFirstName, String customerLastName, String address, String zipCode,
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
                clientState = state;
                System.out.println("state added");
            }
        }
        for(Region region : Region.allRegionsList) {
            if(region.getRegionId() == this.regionId) {
                clientRegion = region;
                System.out.println("region added");
            }
        }
        for(Salesperson salesperson : Salesperson.allSalespersonsList) {
            if(salesperson.getSalespersonId() == this.salespersonId) {
                clientSalesperson = salesperson;
                System.out.println("salesperson added");
            }
        }
    }

    /** Sets the ClientId instance variable.
     * @param clientId clientId as an int. */
    public void setClientId(int clientId) { this.clientId = clientId; }
    /** Sets the CustomerFirstName instance variable.
     * @param customerFirstName customer first name as a string. */
    public void setCustomerFirstName(String customerFirstName) { this.clientFirstName = customerFirstName; }
    /** Sets the CustomerLastName instance variable.
     * @param customerLastName customer last name as a string. */
    public void setCustomerLastName(String customerLastName) { this.clientLastName = customerLastName; }
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
    public int getClientId() { return clientId; }
    /** Method returns the clientFirstName field.
     * @return clientFirstName as a string. */
    public String getClientFirstName() { return clientFirstName; }
    /** Method returns the clientLastName field.
     * @return clientLastName as a string. */
    public String getClientLastName() { return clientLastName; }
    /** Method returns the customer's concatenated first and last names.
     * @return Full name as a string. */
    public String getClientFullName() { return getClientFirstName() + " " + getClientLastName(); }
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
    public String getRegionName() { return clientRegion.getRegionName(); }
    /** Method returns the State object belonging to this client instance.
     * @return clientState as a State. */
    public State getClientState() { return clientState; }
    /** Method returns the Region object belonging to this client instance.
     * @return clientRegion as a Region. */
    public Region getClientRegion() { return clientRegion; }
    /** Method returns the Salesperson object belonging to this client instance.
     * @return customerSalesperson as a Salesperson. */
    public Salesperson getClientSalesperson() { return clientSalesperson;}
    /** Method returns the total appointments belonging to this client instance for use in tableviews.
     * @return totalAppointments as an int. */
    public int getTotalAppointments() {
        int totalAppointments = 0;
        for (Appointment appointment : Appointment.allAppointmentsList) {
            if(appointment.getClientId() == getClientId()) {
                totalAppointments += 1;
            }
        }
        return totalAppointments;
    }

    /** This method is used to traverse the allClientList to search for a matching ID or partial matching ID's.
     * @param searchInt an int representing an ID to search for
     * @return an observable list containing the search results
     * */
    public static ObservableList<Client> searchCustomers(int searchInt) {
        ObservableList<Client> customersFoundList = FXCollections.observableArrayList();
        for (Client client : allClientList) {
            if (Integer.toString(client.getClientId()).contains(Integer.toString(searchInt))) {
                customersFoundList.add(client);
            }
        }
        return customersFoundList;
    }

    /** This method is used to traverse the allClientList to search for any partial name matches.
     * @param searchString a string with a name or partial name to search for.
     * @return an observable list containing the search results
     * */
    public static ObservableList<Client> searchCustomers(String searchString) {
        ObservableList<Client> clientFoundList = FXCollections.observableArrayList();
        for (Client client : allClientList) {
            if (client.getClientFullName().contains(searchString)) {
                clientFoundList.add(client);
            }
        }
        return clientFoundList;
    }


    /** Method overrides the inherited toString() method to return the userName field. */
    @Override
    public String toString() {
        return getClientFirstName() + " " + getClientLastName();
    }

}