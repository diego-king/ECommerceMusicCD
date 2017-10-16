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
    private AddressInfo addressInfo;
    private ShippingInfo shippingInfo;
    private List<PoItem> poItems;

    public PurchaseEntry() {
        this.customer = new Customer();
        this.addressInfo = new AddressInfo();
        this.shippingInfo = new ShippingInfo();
        this.poItems = new ArrayList<>();
    }

    public PurchaseEntry(Customer customer, AddressInfo addressInfo, ShippingInfo shippingInfo, List<PoItem> poItems) {
        this.customer = customer;
        this.addressInfo = addressInfo;
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

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
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
                ", addressInfo=" + addressInfo +
                ", shippingInfo=" + shippingInfo +
                ", poItems=" + poItems +
                '}';
    }
}
