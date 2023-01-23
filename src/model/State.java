package model;

import database.DBRegions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the class for creating objects representing States that exist in the database.
 *  Each State has an ID, name, Division, and DivisionId.
 * @author Gregory Farrell
 * @version 1.1
 */
public class State {
    /** Static ObservableList field for accessing all States so that the database only needs to be queried once when
     * the application initially launches.
     * */
    public static ObservableList<State> allStatesList = FXCollections.observableArrayList();
    /** Static ObservableList field for accessing all Northeast States so that the database only needs to be queried
     * once when the application initially launches.
     * */
    public static ObservableList<State> northeastRegionStatesList = FXCollections.observableArrayList();
    /** Static ObservableList field for accessing all Southeast States so that the database only needs to be queried
     * once when the application initially launches.
     * */
    public static ObservableList<State> southeastRegionStatesList = FXCollections.observableArrayList();
    /** Static ObservableList field for accessing all Mid-West States so that the database only needs to be queried
     * once when the application initially launches.
     * */
    public static ObservableList<State> midwestRegionStatesList = FXCollections.observableArrayList();
    /** Static ObservableList field for accessing all Texas+ States so that the database only needs to be queried
     * once when the application initially launches.
     * */
    public static ObservableList<State> texasPlusRegionStatesList = FXCollections.observableArrayList();
    /** Static ObservableList field for accessing all California+ States so that the database only needs to be queried
     * once when the application initially launches.
     * */
    public static ObservableList<State> californiaPlusRegionStatesList = FXCollections.observableArrayList();
    /** Static ObservableList field for accessing all Northwest States so that the database only needs to be queried
     * once when the application initially launches.
     * */
    public static ObservableList<State> northwestRegionStatesList = FXCollections.observableArrayList();

    /** StateId as an int. */
    private int stateId;
    /** State name as a string. */
    private String stateName;
    /** State abbreviation as a string. */
    private String stateAbbreviation;
    /** Region object that each State object belongs to. */
    private Region region;
    /** RegionId as an int. */
    private int regionId;

    /** Constructor for instantiating State objects.
     * This method pulls all the Regions from the database and then loops through them in order to assign the Region
     * object that matches the regionId.
     * @param stateId State ID as an int.
     * @param stateName State name as a string.
     * @param stateAbbreviation State abbreviation as a string.
     * @param regionId Region ID as an int.
     * */
    public State(int stateId, String stateName, String stateAbbreviation, int regionId) {
        setStateId(stateId);
        setStateName(stateName);
        setStateAbbreviation(stateAbbreviation);
        setRegionId(regionId);
    }

    /** Sets the stateId instance variable.
     * @param stateId stateId as an int. */
    public void setStateId(int stateId) { this.stateId = stateId; }
    /** Sets the stateName instance variable.
     * @param stateName stateName as a string */
    public void setStateName(String stateName) { this.stateName = stateName; }
    /** Sets the stateAbbreviation instance variable.
     * @param stateAbbreviation stateAbbreviation as a string */
    public void setStateAbbreviation(String stateAbbreviation) { this.stateAbbreviation = stateAbbreviation; }
    /** Sets the regionId instance variable.
     * @param regionId regionId as an int. */
    public void setRegionId(int regionId) { this.regionId = regionId; }

    /** Method returns the stateId field.
     *  @return stateId as an int. */
    public int getStateId() { return stateId; }
    /** Method returns the stateName field.
     *  @return stateName as a string. */
    public String getStateName() { return stateName; }
    /** Method returns the stateAbbreviation field.
     *  @return stateAbbreviation as a string. */
    public String getStateAbbreviation() { return stateAbbreviation; }
    /** Method returns the regionId field.
     *  @return regionId as an int. */
    public int getRegionId() { return regionId; }

    /** Method overrides the inherited toString() method to return the stateName field. */
    @Override
    public String toString() {
        return getStateName();
    }
}
