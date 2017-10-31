package ca.edu.uottawa.csi5380.model;

import ca.edu.uottawa.csi5380.model.builders.AddressInfoBuilder;
import ca.edu.uottawa.csi5380.model.builders.CustomerBuilder;

/**
 * Holds Customer information, as well as additional shipping
 * information including their default Billing and Shipping address.
 *
 * @author Kenny Byrd
 */
public class Account {

    private Customer customer;
    private AddressInfo defaultAddressInfo;

    public Account() {
        this.customer = new CustomerBuilder().createCustomer();
        this.defaultAddressInfo = new AddressInfoBuilder().createAddressInfo();
    }

    public Account(Customer customer) {
        this.customer = customer;
        this.defaultAddressInfo = new AddressInfoBuilder().createAddressInfo();
    }

    public Account(Customer customer, AddressInfo defaultAddressInfo) {
        this.customer = customer;
        this.defaultAddressInfo = defaultAddressInfo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AddressInfo getDefaultAddressInfo() {
        return defaultAddressInfo;
    }

    public void setDefaultAddressInfo(AddressInfo defaultAddressInfo) {
        this.defaultAddressInfo = defaultAddressInfo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "customer=" + customer +
                ", defaultAddressInfo=" + defaultAddressInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (!getCustomer().equals(account.getCustomer())) return false;
        return getDefaultAddressInfo().equals(account.getDefaultAddressInfo());
    }

    @Override
    public int hashCode() {
        int result = getCustomer().hashCode();
        result = 31 * result + getDefaultAddressInfo().hashCode();
        return result;
    }
}
