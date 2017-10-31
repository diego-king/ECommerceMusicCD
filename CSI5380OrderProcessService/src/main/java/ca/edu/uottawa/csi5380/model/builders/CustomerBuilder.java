package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.Customer;

public class CustomerBuilder {
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String password = "";
    private long id = - 1;
    private long defaultShippingAddressId = -1;
    private long defaultBillingAddressId = -1;

    public CustomerBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public CustomerBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder setDefaultShippingAddressId(long defaultShippingAddressId) {
        this.defaultShippingAddressId = defaultShippingAddressId;
        return this;
    }

    public CustomerBuilder setDefaultBillingAddressId(long defaultBillingAddressId) {
        this.defaultBillingAddressId = defaultBillingAddressId;
        return this;
    }

    public Customer createCustomer() {
        return new Customer(id, firstName, lastName, email, password, defaultShippingAddressId, defaultBillingAddressId);
    }
}