package model;

import database.DBCountries;

/** This is the class for creating objects representing Divisions that exist in the database.
 *  Each Division has an ID, name, Country, and CountryId.
 * @author Gregory Farrell
 * @version 1.0
 */
public class Division {
    /** DivisionId as an int. */
    private int divisionId;
    /** Division name as a string. */
    private String divisionName;
    /** Country object that belongs to each Division object. */
    private Country country;
    /** CountryId as an int. */
    private int countryId;

    /** Constructor for instantiating Division objects.
     * This method pulls all of the Countries from the database and then loops through them in order to assign a Country
     * object that matches the countryId.
     * @param divisionId Division ID as an int.
     * @param divisionName Division name as a string.
     * @param countryId Country ID as an int.
     * */
    public Division(int divisionId, String divisionName, int countryId) {
        setDivisionId(divisionId);
        setDivisionName(divisionName);
        setCountryId(countryId);
        DBCountries.getAllCountries();
        for (Country c : DBCountries.getAllCountries()) {
            if (countryId == c.getCountryId()) {
                this.country = c;
                return;
            }
        }
    }

    /** Sets the DivisionId instance variable.
     * @param divisionId divisionId as an int. */
    public void setDivisionId(int divisionId) { this.divisionId = divisionId; }
    /** Sets the divisionName instance variable.
     * @param divisionName divisionName as a string */
    public void setDivisionName(String divisionName) { this.divisionName = divisionName; }
    /** Sets the CountryId instance variable.
     * @param countryId countryId as an int. */
    public void setCountryId(int countryId) { this.countryId = countryId; }

    /** Method returns the divisionId field.
     *  @return divisionId as an int. */
    public int getDivisionId() { return divisionId; }
    /** Method returns the divisionNamefield.
     *  @return divisionName as an string. */
    public String getDivisionName() { return divisionName; }
    public int getCountryId() { return countryId; }
    /** Method returns the country field
     * @return country as a Country object. */
    public Country getCountry() { return country; }

    /** Method overrides the inherited toString() method to return the divisionName field. */
    @Override
    public String toString() {
        return getDivisionName();
    }
 }

