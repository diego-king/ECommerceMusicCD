package ca.edu.uottawa.csi5380.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class PurchaseOrder {

    private long id;
    private long customerId;
    private long shippingAddressId;
    private long billingAddressId;
    private long shippingTypeId;

    private BigDecimal subTotal;
    private BigDecimal grandTotal;
    private Instant date;
    private PoStatus status;

    // Required to create new order
    private PurchaseEntry purchaseEntry;

    public PurchaseOrder() {
        this.id = -1;
        this.customerId = -1;
        this.shippingAddressId = -1;
        this.billingAddressId = -1;
        this.shippingTypeId = -1;
        this.date = Instant.now();
        this.status = PoStatus.ORDERED;
        this.subTotal = new BigDecimal(0);
        this.grandTotal = new BigDecimal(0);
        this.purchaseEntry = new PurchaseEntry();
    }

    public PurchaseOrder(PurchaseEntry purchaseEntry) {
        this();
        this.purchaseEntry = purchaseEntry;
    }

    public PurchaseOrder(long id, long customerId, long shippingAddressId, long billingAddressId, long shippingTypeId, Instant date, PoStatus status,
                         BigDecimal subTotal, BigDecimal grandTotal) {
        this.id = id;
        this.customerId = customerId;
        this.shippingAddressId = shippingAddressId;
        this.billingAddressId = billingAddressId;
        this.shippingTypeId = shippingTypeId;
        this.date = date;
        this.status = status;
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
    }

    public PurchaseOrder(long id, Instant date, String status, BigDecimal subTotal, BigDecimal grandTotal) {
        this.id = id;
        this.date = date;
        this.status = PoStatus.valueOf(status);
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
    }

    public PurchaseOrder(long id, Instant date, PoStatus status, BigDecimal subTotal, BigDecimal grandTotal) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public long getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(long billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public long getShippingTypeId() {
        return shippingTypeId;
    }

    public void setShippingTypeId(long shippingTypeId) {
        this.shippingTypeId = shippingTypeId;
    }

    public Instant getDate() {
        return date;
    }

    @JsonProperty("date")
    public String getDateString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(date));
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public PoStatus getStatus() {
        return status;
    }

    public void setStatus(PoStatus status) {
        this.status = status;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public PurchaseEntry getPurchaseEntry() {
        return purchaseEntry;
    }

    public void setPurchaseEntry(PurchaseEntry purchaseEntry) {
        this.purchaseEntry = purchaseEntry;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", shippingAddressId=" + shippingAddressId +
                ", billingAddressId=" + billingAddressId +
                ", shippingTypeId=" + shippingTypeId +
                ", subTotal=" + subTotal +
                ", grandTotal=" + grandTotal +
                ", date=" + date +
                ", status=" + status +
                ", purchaseEntry=" + purchaseEntry +
                '}';
    }
}
