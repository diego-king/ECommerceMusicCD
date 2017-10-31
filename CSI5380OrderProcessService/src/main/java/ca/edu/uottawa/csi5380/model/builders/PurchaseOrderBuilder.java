package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.PoStatus;
import ca.edu.uottawa.csi5380.model.PurchaseOrder;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;

public class PurchaseOrderBuilder {
    private long id = -1;
    private long customerId = -1;
    private long shippingAddressId = -1;
    private long billingAddressId = -1;
    private long shippingTypeId = -1;
    private Instant date = Instant.now();
    private PoStatus status = PoStatus.ORDERED;
    private BigDecimal subTotal = new BigDecimal(0);
    private BigDecimal grandTotal = new BigDecimal(0);
    private BigDecimal taxTotal = new BigDecimal(0);
    private BigDecimal shippingTotal = new BigDecimal(0);

    public PurchaseOrderBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public PurchaseOrderBuilder setCustomerId(long customerId) {
        this.customerId = customerId;
        return this;
    }

    public PurchaseOrderBuilder setShippingAddressId(long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
        return this;
    }

    public PurchaseOrderBuilder setBillingAddressId(long billingAddressId) {
        this.billingAddressId = billingAddressId;
        return this;
    }

    public PurchaseOrderBuilder setShippingTypeId(long shippingTypeId) {
        this.shippingTypeId = shippingTypeId;
        return this;
    }

    public PurchaseOrderBuilder setDate(Instant date) {
        this.date = date;
        return this;
    }

    public PurchaseOrderBuilder setStatus(PoStatus status) {
        this.status = status;
        return this;
    }

    public PurchaseOrderBuilder setStatus(String status) {
        this.status = StringUtils.isNumeric(status) ? PoStatus.values()[Integer.parseInt(status)] : PoStatus.valueOf(status);
        return this;
    }

    public PurchaseOrderBuilder setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public PurchaseOrderBuilder setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
        return this;
    }

    public PurchaseOrderBuilder setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
        return this;
    }

    public PurchaseOrderBuilder setShippingTotal(BigDecimal shippingTotal) {
        this.shippingTotal = shippingTotal;
        return this;
    }

    public PurchaseOrder createPurchaseOrder() {
        return new PurchaseOrder(id, customerId, shippingAddressId, billingAddressId, shippingTypeId, date, status, subTotal, grandTotal, taxTotal, shippingTotal);
    }
}