package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the class for creating objects representing Regions that exist in the database.
 *  Each Region has a name and an ID.
 * @author Gregory Farrell
 * @version 1.1
 */
public class Region {
    /** Static ObservableList field for accessing all Regions so that the database only needs to be queried once when
     * the application initially launches.
     * */
    public static ObservableList<Region> allRegionsList = FXCollections.observableArrayList();

    /** RegionId as an int. */
    private int regionId;
    /** RegionName as a string. */
    private String regionName;

    /** Constructor for instantiating Region objects.
     * @param regionId regionId as an int.
     * @param regionName regionName as a string.
     * */
    public Region(int regionId, String regionName) {
        setRegionId(regionId);
        setRegionName(regionName);
    }

    /** Method sets the regionId field.
     * @param regionId  regionId as an int.
     * */
    public void setRegionId(int regionId) { this.regionId = regionId; }
    /** Method sets the regionName field.
     * @param regionName regionName as a string.
     * */
    public void setRegionName(String regionName) { this.regionName = regionName; }

    /** Method returns the regionId field.
     * @return regionId as an int.
     * */
    public int getRegionId() { return regionId; }
    /** Method returns the regionName field.
     * @return regionName as a string.
     * */
    public String getRegionName() { return regionName; }

    /** Method overrides the inherited toString() method to return the regionName field.
     * @return regionName as a string.
     */
    @Override
    public String toString() { return getRegionName(); }
}
