package ca.edu.uottawa.csi5380.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Purchase Entry represents a new purchase that the Customer
 * has "checked" out from the Shopping Cart Page.
 *
 * Note: Use this object to create the initial order.
 *
 * @author Kenny Byrd
 */
public class PurchaseEntry {

    private Customer customer;
    private Address billingAddress;
    private Address shippingAddress;
    private ShippingInfo shippingInfo;
    private List<PoItem> poItems;

    public PurchaseEntry() {
        this.customer = new Customer();
        this.billingAddress = new Address();
        this.shippingAddress = new Address();
        this.shippingInfo = new ShippingInfo();
        this.poItems = new ArrayList<>();
    }

    public PurchaseEntry(Customer customer, Address billingAddress, Address shippingAddress, ShippingInfo shippingInfo, List<PoItem> poItems) {
        this.customer = customer;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.shippingInfo = shippingInfo;
        this.poItems = poItems;
    }

    // Getters & Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public List<PoItem> getPoItems() {
        return poItems;
    }

    public void setPoItems(List<PoItem> poItems) {
        this.poItems = poItems;
    }

    @Override
    public String toString() {
        return "PurchaseEntry{" +
                "customer=" + customer +
                ", billingAddress=" + billingAddress +
                ", shippingAddress=" + shippingAddress +
                ", shippingInfo=" + shippingInfo +
                ", poItems=" + poItems +
                '}';
    }
}
