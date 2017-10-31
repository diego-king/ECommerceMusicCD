package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.PoItem;
import ca.edu.uottawa.csi5380.model.PurchaseEntry;

import java.util.ArrayList;
import java.util.List;

public class PurchaseEntryBuilder {
    private String customerEmail = "";
    private long shippingInfoId = -1;
    private List<PoItem> poItems = new ArrayList<>();

    public PurchaseEntryBuilder setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public PurchaseEntryBuilder setShippingInfoId(long shippingInfoId) {
        this.shippingInfoId = shippingInfoId;
        return this;
    }

    public PurchaseEntryBuilder setPoItems(List<PoItem> poItems) {
        this.poItems = poItems;
        return this;
    }

    public PurchaseEntry createPurchaseEntry() {
        return new PurchaseEntry(customerEmail, shippingInfoId, poItems);
    }
}