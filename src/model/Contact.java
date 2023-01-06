package model;

/** This is the Contact class for creating objects representing Contacts that exist in the database.
 *  Each Contact has an ID number, name and email address
 * @author Gregory Farrell
 * @version 1.0
 */
public class Contact {
    /** Contact ID as an int. */
    private int contactId;
    /** Contact name as a string. */
    private String contactName;
    /** Contact email as a string. */
    private String contactEmail;

    /** Constructor used to create Contact objects.
     * This constructor is used for creating Contact objects from existing contacts in the database.
     * @param contactId contactId as an int.
     * @param contactName contactName as a string.
     * @param contactEmail contactEmail as a string. */
    public Contact(int contactId, String contactName, String contactEmail) {
        setContactId(contactId);
        setName(contactName);
        setEmail(contactEmail);
    }

    /** Method sets the contactId field.
     * @param contactId contactId as an int.
     * */
    public void setContactId(int contactId) { this.contactId = contactId; }
    /** Method sets the contactName field.
     * @param name contactName as a string.
     * */
    public void setName(String name) { this.contactName = name; }
    /** Method sets the contactEmail field.
     * @param email contactEmail as a string.
     * */
    public void setEmail(String email) { this.contactEmail = email; }

    /** Method returns the contactId field.
     * @return contactId as an int. */
    public int getId() { return contactId; }
    /** Method returns the contactName field.
     * @return contactName as a string. */
    public String getContactName() { return contactName; }
    /** Method returns the contactEmail field.
     * @return contactEmail as a string. */
    public String getEmail() { return contactEmail; }

    /** Method overrides the inherited toString() method to return the contactName field.
     * @return contactName as a string. */
    public String toString() { return this.contactName; }
}
