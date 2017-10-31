package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.Address;
import ca.edu.uottawa.csi5380.model.AddressInfo;

public class AddressInfoBuilder {
    private Address billingAddress = new AddressBuilder().createAddress();
    private Address shippingAddress = new AddressBuilder().createAddress();

    public AddressInfoBuilder setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public AddressInfoBuilder setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public AddressInfo createAddressInfo() {
        return new AddressInfo(billingAddress, shippingAddress);
    }
}