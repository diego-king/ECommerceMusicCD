package ca.edu.uottawa.csi5380.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder {

    // NOTE: Don't need to set these ID's when creating a new order
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
    private Customer customer;
    private List<Address> addressList;
    private ShippingInfo shippingInfo;
    private List<PoItem> poItems;

    public PurchaseOrder() {
        this.id = -1;
        this.customerId = -1;
        this.shippingAddressId = -1;
        this.billingAddressId = -1;
        this.shippingTypeId = -1;
        this.customer = new Customer();
        this.addressList = new ArrayList<>();
        this.shippingInfo = new ShippingInfo();
        this.date = Instant.now();
        this.status = PoStatus.ORDERED;
        this.subTotal = new BigDecimal(0);
        this.grandTotal = new BigDecimal(0);
        this.poItems = new ArrayList<>();
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

    public PurchaseOrder(Customer customer, List<Address> addressList, ShippingInfo shippingInfo, List<PoItem> PoItems) {
        this();
        this.customer = customer;
        this.addressList = addressList;
        this.shippingInfo = shippingInfo;
        this.poItems = PoItems;
    }

    public PurchaseOrder(long id, Customer customer, List<Address> addressList, Instant date, String status, BigDecimal subTotal, BigDecimal grandTotal, List<PoItem> PoItems) {
        this.id = id;
        this.customer = customer;
        this.addressList = addressList;
        this.date = date;
        this.status = PoStatus.valueOf(status);
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
        this.poItems = PoItems;
    }

    public PurchaseOrder(long id, Customer customer, List<Address> addressList, Instant date, PoStatus status, BigDecimal subTotal, BigDecimal grandTotal, List<PoItem> PoItems) {
        this.id = id;
        this.customer = customer;
        this.addressList = addressList;
        this.date = date;
        this.status = status;
        this.subTotal = subTotal;
        this.grandTotal = grandTotal;
        this.poItems = PoItems;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public Instant getDate() {
        return date;
    }

    @JsonProperty("date")
    public String getDateString() {
        return date.toString();
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
                ", customer=" + customer +
                ", addressList=" + addressList +
                ", shippingInfo=" + shippingInfo +
                ", date=" + date +
                ", status=" + status +
                ", subTotal=" + subTotal +
                ", grandTotal=" + grandTotal +
                ", poItems=" + poItems +
                '}';
    }
}
