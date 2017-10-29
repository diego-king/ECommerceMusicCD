package ca.edu.uottawa.csi5380.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Represents a purchase order, mapping directly to the PO table
 * in the database.
 * <p>
 * This class holds information about the Customer who
 * ordered the specific order, the shipping and billing address
 * the Customer has used for this specific order, the subtotal,
 * grand total, tax amount, shipping amount, date the order was created,
 * and the current status of the order.
 * </p>
 * @author Kenny Byrd
 */
public class PurchaseOrder {

    private long id;
    private long customerId;
    private long shippingAddressId;
    private long billingAddressId;
    private long shippingTypeId;

    private BigDecimal subTotal;
    private BigDecimal grandTotal;
    private BigDecimal taxTotal;
    private BigDecimal shippingTotal;
    private Instant date;
    private PoStatus status;

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private ShippingInfo shippingInfo;
    private List<PoItem> poItems; // Retrieve using id and customerId

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
        this.taxTotal = new BigDecimal(0);
        this.shippingTotal = new BigDecimal(0);
    }

    public PurchaseOrder(long id, long customerId, long shippingAddressId, long billingAddressId, long shippingTypeId,
                         Instant date, PoStatus status, BigDecimal subTotal, BigDecimal grandTotal, BigDecimal taxTotal,
                         BigDecimal shippingTotal) {
        this.id = id;
        this.customerId = customerId;
        this.shippingAddressId = shippingAddressId;
        this.billingAddressId = billingAddressId;
        this.shippingTypeId = shippingTypeId;
        this.date = date;
        this.status = status;
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
        this.taxTotal = taxTotal;
        this.shippingTotal = shippingTotal;
    }

    public PurchaseOrder(long id, Instant date, String status, BigDecimal subTotal, BigDecimal grandTotal, BigDecimal taxTotal,
                         BigDecimal shippingTotal) {
        this.id = id;
        this.date = date;
        this.status = PoStatus.valueOf(status);
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
        this.taxTotal = taxTotal;
        this.shippingTotal = shippingTotal;
    }

    public PurchaseOrder(long id, Instant date, PoStatus status, BigDecimal subTotal, BigDecimal grandTotal, BigDecimal taxTotal,
            BigDecimal shippingTotal) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
        this.taxTotal = taxTotal;
        this.shippingTotal = shippingTotal;
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

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public BigDecimal getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(BigDecimal shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
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
        return "PurchaseOrder{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", shippingAddressId=" + shippingAddressId +
                ", billingAddressId=" + billingAddressId +
                ", shippingTypeId=" + shippingTypeId +
                ", subTotal=" + subTotal +
                ", grandTotal=" + grandTotal +
                ", taxTotal=" + taxTotal +
                ", shippingTotal=" + shippingTotal +
                ", date=" + date +
                ", status=" + status +
                ", customer=" + customer +
                ", shippingAddress=" + shippingAddress +
                ", billingAddress=" + billingAddress +
                ", shippingInfo=" + shippingInfo +
                ", poItems=" + poItems +
                '}';
    }
}
