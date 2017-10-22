package ca.edu.uottawa.csi5380.database.agent.order;

import ca.edu.uottawa.csi5380.database.agent.DataAgent;
import ca.edu.uottawa.csi5380.database.agent.utils.DataUtils;
import ca.edu.uottawa.csi5380.exception.RestDaoException;
import ca.edu.uottawa.csi5380.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    // SQL SELECT KEYS
    private static final String SQL_SELECT_LAST_INSERT_ID = "sql.select.last.insert.id";
    private static final String SQL_SELECT_SHIPPING_METHODS = "sql.select.shipping";
    private static final String SQL_SELECT_SHIPPING_METHOD_BY_ID = "sql.select.shipping.by.id";
    private static final String SQL_SELECT_ORDER_BY_ID = "sql.select.order.by.id";
    private static final String SQL_SELECT_CUSTOMER_BY_EMAIL = "sql.select.customer.by.email";

    // SQL INSERT KEYS
    private static final String SQL_INSERT_ADDRESS = "sql.insert.address";
    private static final String SQL_INSERT_ORDER = "sql.insert.order";
    private static final String SQL_INSERT_PO_ITEM = "sql.insert.poitem";

    // SQL UPDATE KEYS
    private static final String SQL_UPDATE_ORDER_STATUS = "sql.update.order.status";
    private static final String SQL_UPDATE_ORDER_ADDRESSES = "sql.update.order.address";
    private static final String SQL_UPDATE_ADDRESS_BY_ID = "sql.update.address.by.id";

    private final DataAgent dataAgent;

    @Autowired
    public OrderDaoImpl(DataAgent dataAgent) {
        this.dataAgent = dataAgent;
    }

    @Override
    public List<ShippingInfo> getShippingInfo() {
        return DataUtils.getShippingInfoFromResult(dataAgent.getQueryResult(SQL_SELECT_SHIPPING_METHODS, new Object[]{}));
    }

    @Override
    public void createOrder(PurchaseEntry p) {

        // Error checking
        validatePurchaseEntry(p);

        PurchaseOrder po = new PurchaseOrder();

        // Get the necessary shipping info from the DB
        ShippingInfo shippingInfo = getShippingInfoById(p.getShippingInfoId());

        // Set customer username and ID
        po.setCustomer(getCustomerByUsername(p.getCustomerEmail()));
        po.setCustomerId(po.getCustomer().getId());

        // Set Shipping Address ID
        po.setShippingAddressId(po.getCustomer().getDefaultShippingAddressId());

        // Set Billing Address ID
        po.setBillingAddressId(po.getCustomer().getDefaultBillingAddressId());

        // Set Shipping Type ID
        po.setShippingTypeId(shippingInfo.getId());

        // Calculate sub total
        po.setSubTotal(calcSubtotal(p.getPoItems()));

        // Set Shipping Cost
        po.setShippingTotal(shippingInfo.getPrice());

        // Calculate tax amount ((subtotal + shipping) * 0.13)
        po.setTaxTotal(calcTax(po.getSubTotal(), po.getShippingTotal()));

        // Calculate grand total (subtotal + shipping + tax)
        po.setGrandTotal(calcGrandTotal(po.getSubTotal(), po.getShippingTotal(), po.getTaxTotal()));

        // Create new purchase order with status "ORDERED"
        insertPurchaseOrder(po);

        po.setId(getLastInsertId());

        // Insert Purchase Order Item(s) using the CD ID and Order ID
        for (PoItem poItem : p.getPoItems()) {
            poItem.setPoId(po.getId());
            insertPoItem(poItem);
        }

    }

    private BigDecimal calcSubtotal(List<PoItem> orderedItems) {
        BigDecimal subTotal = new BigDecimal(0);
        for (PoItem orderedItem : orderedItems) {
            subTotal = subTotal.add(orderedItem.getTotalPrice());
        }
        return subTotal;
    }

    private BigDecimal calcTax(BigDecimal subTotal, BigDecimal shippingCost) {
        return new BigDecimal(0).add(subTotal).add(shippingCost).multiply(new BigDecimal(0.13));
    }

    private BigDecimal calcGrandTotal(BigDecimal subTotal, BigDecimal shippingCost, BigDecimal tax) {
        return new BigDecimal(0).add(subTotal).add(shippingCost).add(tax);
    }

    @Override
    public boolean confirmOrder(long orderId, boolean isAuthorized, AddressInfo addressInfo) {

        // Update the Purchase Order's status to PROCESSED or DENIED
        updatePurchaseOrderStatus(isAuthorized ? PoStatus.PROCESSED : PoStatus.DENIED, orderId);

        // Update the Customer's billing address for existing order
        insertAddress(addressInfo.getShippingAddress());
        long shippingId = getLastInsertId();

        // Update the Customer's shipping address for existing order
        insertAddress(addressInfo.getBillingAddress());
        long billingId = getLastInsertId();

        // Update the Purchase Order's shipping and billing address with their verified address
        updateOrderAddresses(shippingId, billingId, orderId);

        return isAuthorized;
    }

    /**
     * Insert an address into the database given the address.
     *
     * @param a - Address to insert
     */
    private void insertAddress(Address a) {
        dataAgent.executeSQL(SQL_INSERT_ADDRESS, new Object[]{a.getFullName(), a.getAddressLine1(),
                a.getAddressLine2(), a.getCity(), a.getProvince(), a.getCountry(), a.getZip(),
                a.getPhone(), a.getType().toString()});
    }


    private Customer getCustomerByUsername(String username) {
        return DataUtils.getCustomerFromResult(dataAgent.getQueryResult(SQL_SELECT_CUSTOMER_BY_EMAIL, new Object[]{username}));
    }

    private long getLastInsertId() {
        return DataUtils.getLastInsertIdFromResult(dataAgent.getQueryResult(SQL_SELECT_LAST_INSERT_ID, new Object[]{}));
    }

    private void updateAddressById(Address a) {
        dataAgent.executeSQL(SQL_UPDATE_ADDRESS_BY_ID, new Object[]{a.getAddressLine1(), a.getAddressLine2(), a.getCity(),
                a.getProvince(), a.getCountry(), a.getZip(), a.getPhone(), a.getId()});
    }

    private void updatePurchaseOrderStatus(PoStatus status, long id) {
        dataAgent.executeSQL(SQL_UPDATE_ORDER_STATUS, new Object[]{status.toString(), id});
    }

    private void updateOrderAddresses(long shippingId, long billingId, long orderId) {
        dataAgent.executeSQL(SQL_UPDATE_ORDER_ADDRESSES, new Object[]{shippingId, billingId, orderId});
    }

    private PurchaseOrder getPurchaseOrderById(long id) {
        return DataUtils.getPurchaseOrderFromResult(dataAgent.getQueryResult(SQL_SELECT_ORDER_BY_ID, new Object[]{id}));
    }

    private void insertPurchaseOrder(PurchaseOrder po) {
        dataAgent.executeSQL(SQL_INSERT_ORDER, new Object[]{po.getCustomerId(), po.getShippingAddressId(),
                po.getBillingAddressId(), po.getShippingTypeId(), po.getDateString(), po.getSubTotal(),
                po.getGrandTotal(), po.getTaxTotal(), po.getShippingTotal()});
    }

    private void insertPoItem(PoItem poItem) {
        dataAgent.executeSQL(SQL_INSERT_PO_ITEM, new Object[]{poItem.getPoId(), poItem.getCdId(), poItem.getUnitPrice(), poItem.getNumOrdered()});
    }

    private ShippingInfo getShippingInfoById(long id) {
        return DataUtils.getShippingInfoByIdFromResult(dataAgent.getQueryResult(SQL_SELECT_SHIPPING_METHOD_BY_ID, new Object[]{id}));
    }

    /**
     * Performs validation on the Purchase Entry to make sure it contains
     * all the necessary information to create a new Purchase Order.
     *
     * @param p
     */
    private void validatePurchaseEntry(PurchaseEntry p) {

        if (p.getShippingInfoId() < 1) {
            throw new RestDaoException("Invalid shipping info ID. Please provide an ID for Shipping information.");
        }

        if (StringUtils.isBlank(p.getCustomerEmail())) {
            throw new RestDaoException("Invalid customer ID. Please provide a Customer email (username).");
        }

    }

}
