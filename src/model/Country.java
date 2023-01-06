package model;

/** This is the class for creating objects representing Countries that exist in the database.
 *  Each Country has a name and an ID.
 * @author Gregory Farrell
 * @version 1.0
 */
public class Country {
    /** CountryId as an int. */
    private int countryId;
    /** CountryName as an string. */
    private String countryName;

    /** Constructor for instantiating Country objects.
     * @param countryId countryId as an int.
     * @param countryName countryName as a string.
     * */
    public Country(int countryId, String countryName) {
        setCountryId(countryId);
        setCountryName(countryName);
    }

    /** Method sets the countryId field.
     * @param countryId  countryId as an int.
     * */
    public void setCountryId(int countryId) { this.countryId = countryId; }
    /** Method sets the countryName field.
     * @param countryName countryName as a string.
     * */
    public void setCountryName(String countryName) { this.countryName = countryName; }

    /** Method returns the countryId field.
     * @return countryId as an int.
     * */
    public int getCountryId() { return countryId; }
    /** Method returns the countryName field.
     * @return countryName as a string.
     * */
    public String getCountryName() { return countryName; }

    /** Method overrides the inherited toString() method to return the countryName field.
     * @return countryName as a string.
     */
    @Override
    public String toString() { return getCountryName(); }
}
