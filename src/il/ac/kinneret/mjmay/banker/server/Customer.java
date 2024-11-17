package il.ac.kinneret.mjmay.banker.server;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a customer in the bank for the RMI banking example
 *
 * @author Michael J. May
 */
public class Customer implements Serializable {

    /**
     * The serial id for the class
     */
    @Serial
    private static final long serialVersionUID = -4385080825232129934L;
    /**
     * The id number for the customer
     */
    private final String tz;
    /**
     * The customer's name
     */
    private String name;
    /**
     * The customer's address
     */
    private String address;
    /**
     * The customer's city
     */
    private String city;

    /**
     * Builds a new customer instance
     *
     * @param tz  Customer's id number
     * @param n   Customer's name
     * @param add Customer's address
     * @param c   Customer's city
     */
    public Customer(String tz, String n, String add, String c) {
        this.name = n;
        this.address = add;
        this.city = c;
        this.tz = tz;
    }

    /**
     * Gets the customer id
     *
     * @return The customer's id
     */
    public String getTZ() {
        return this.tz;
    }

    /**
     * Gets the customer name
     *
     * @return The customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer name
     *
     * @param n The customer name
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * Gets the customer address
     *
     * @return The customer's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the customer city
     *
     * @return The customer's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the customer address
     *
     * @param a The customer's address
     * @param c The customer's city
     */
    public void setAddress(String a, String c) {
        this.address = a;
        this.city = c;
    }

    /**
     * Creates a string representation of the Customerr
     *
     * @return The customer's details
     */
    @Override
    public String toString() {
        return name + " (" + tz + ") " + address + ", " + city;
    }
}

