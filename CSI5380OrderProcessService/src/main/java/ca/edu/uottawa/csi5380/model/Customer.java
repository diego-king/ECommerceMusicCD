package ca.edu.uottawa.csi5380.model;

/**
 * Represents a Customer of the CD Store.
 * <p>
 * It contains basic information about the customer in order to identify
 * them and for accessing their account.
 * </p>
 * This class maps directly to the Customer table in the database.
 *
 * @author Kenny Byrd
 */
public class Customer {

    // Member variables
    private long id;
    private String firstName;
    private String lastName;
    private String email; // Customer's login username
    private String password;
    private long defaultShippingAddressId; // Do not need when creating new account
    private long defaultBillingAddressId; // Do not need when creating new account

    // Default constructor
    public Customer() {
        this.id = -1;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.defaultShippingAddressId = -1;
        this.defaultBillingAddressId = -1;
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Customer(String firstName, String lastName, String email, String password, long defaultShippingAddressId, long defaultBillingAddressId) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.defaultShippingAddressId = defaultShippingAddressId;
        this.defaultBillingAddressId = defaultBillingAddressId;
    }

    public Customer(long id, String firstName, String lastName, String email, String password, long defaultShippingAddressId, long defaultBillingAddressId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.defaultShippingAddressId = defaultShippingAddressId;
        this.defaultBillingAddressId = defaultBillingAddressId;
    }

    // Getters & Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDefaultShippingAddressId() {
        return defaultShippingAddressId;
    }

    public void setDefaultShippingAddressId(long defaultShippingAddressId) {
        this.defaultShippingAddressId = defaultShippingAddressId;
    }

    public long getDefaultBillingAddressId() {
        return defaultBillingAddressId;
    }

    public void setDefaultBillingAddressId(long defaultBillingAddressId) {
        this.defaultBillingAddressId = defaultBillingAddressId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", defaultShippingAddressId=" + defaultShippingAddressId +
                ", defaultBillingAddressId=" + defaultBillingAddressId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getId() != customer.getId()) return false;
        if (getDefaultShippingAddressId() != customer.getDefaultShippingAddressId()) return false;
        if (getDefaultBillingAddressId() != customer.getDefaultBillingAddressId()) return false;
        if (!getFirstName().equals(customer.getFirstName())) return false;
        if (!getLastName().equals(customer.getLastName())) return false;
        if (!getEmail().equals(customer.getEmail())) return false;
        return getPassword().equals(customer.getPassword());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + (int) (getDefaultShippingAddressId() ^ (getDefaultShippingAddressId() >>> 32));
        result = 31 * result + (int) (getDefaultBillingAddressId() ^ (getDefaultBillingAddressId() >>> 32));
        return result;
    }


}
