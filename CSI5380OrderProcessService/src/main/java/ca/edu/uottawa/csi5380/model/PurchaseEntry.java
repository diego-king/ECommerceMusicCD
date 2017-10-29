package ca.edu.uottawa.csi5380.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Purchase Entry represents a new purchase that the Customer
 * has "checked" out from the Shopping Cart Page.
 * <p>
 * Note: Use this object to create the initial order.
 * </p>
 * @author Kenny Byrd
 */
public class PurchaseEntry {

    private String customerEmail; // Customer's email/username
    private long shippingInfoId;
    private List<PoItem> poItems;

    public PurchaseEntry() {
        this.customerEmail = "";
        this.shippingInfoId = -1;
        this.poItems = new ArrayList<>();
    }

    public PurchaseEntry(String customerEmail, long shippingInfoId, List<PoItem> poItems) {
        this.customerEmail = customerEmail;
        this.shippingInfoId = shippingInfoId;
        this.poItems = poItems;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public long getShippingInfoId() {
        return shippingInfoId;
    }

    public void setShippingInfoId(long shippingInfoId) {
        this.shippingInfoId = shippingInfoId;
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
                "customerEmail='" + customerEmail + '\'' +
                ", shippingInfoId=" + shippingInfoId +
                ", poItems=" + poItems +
                '}';
    }
}
