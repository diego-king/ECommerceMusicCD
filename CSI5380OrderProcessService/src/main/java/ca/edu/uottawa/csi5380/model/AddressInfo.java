package ca.edu.uottawa.csi5380.model;

import ca.edu.uottawa.csi5380.model.builders.AddressBuilder;

/**
 * Holds shipping and billing address information for a Customer.
 *
 * @author Kenny Byrd
 */
public class AddressInfo {

    private Address billingAddress;
    private Address shippingAddress;

    public AddressInfo() {
        this.billingAddress = new AddressBuilder().createAddress();
        this.shippingAddress = new AddressBuilder().createAddress();
    }

    public AddressInfo(Address billingAddress, Address shippingAddress) {
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "billingAddress=" + billingAddress +
                ", shippingAddress=" + shippingAddress +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressInfo)) return false;

        AddressInfo that = (AddressInfo) o;

        if (!getBillingAddress().equals(that.getBillingAddress())) return false;
        return getShippingAddress().equals(that.getShippingAddress());
    }

    @Override
    public int hashCode() {
        int result = getBillingAddress().hashCode();
        result = 31 * result + getShippingAddress().hashCode();
        return result;
    }
}
