package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.Account;
import ca.edu.uottawa.csi5380.model.AddressInfo;
import ca.edu.uottawa.csi5380.model.Customer;

public class AccountBuilder {
    private Customer customer = new Customer();
    private AddressInfo defaultAddressInfo = new AddressInfoBuilder().createAddressInfo();

    public AccountBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public AccountBuilder setDefaultAddressInfo(AddressInfo defaultAddressInfo) {
        this.defaultAddressInfo = defaultAddressInfo;
        return this;
    }

    public Account createAccount() {
        return new Account(customer, defaultAddressInfo);
    }
}