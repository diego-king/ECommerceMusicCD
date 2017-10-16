package ca.edu.uottawa.csi5380.database.agent.order;

import ca.edu.uottawa.csi5380.database.agent.DataAgent;
import ca.edu.uottawa.csi5380.database.agent.utils.DataUtils;
import ca.edu.uottawa.csi5380.exception.RestDaoException;
import ca.edu.uottawa.csi5380.model.*;
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

    // SQL INSERT KEYS
    private static final String SQL_INSERT_ORDER = "sql.insert.order";
    private static final String SQL_INSERT_PO_ITEM = "sql.insert.poitem";

    // SQL UPDATE KEYS
    private static final String SQL_UPDATE_ORDER_STATUS = "sql.update.order";
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
    public PurchaseOrder createOrder(PurchaseEntry p) {

        // Error checking
        validatePurchaseEntry(p);

        PurchaseOrder po = new PurchaseOrder(p);

        // Make sure we get the correct shipping info from the DB
        p.setShippingInfo(getShippingInfoById(p.getShippingInfo().getId()));

        // Set customer ID
        po.setCustomerId(p.getCustomer().getId());

        // Set Shipping Address ID
        po.setShippingAddressId(p.getShippingAddress().getId());

        // Set Billing Address ID
        po.setBillingAddressId(p.getBillingAddress().getId());

        // Set Shipping Type ID
        po.setShippingTypeId(p.getShippingInfo().getId());

        // Calculate sub total
        po.setSubTotal(calcSubtotal(p.getPoItems()));

        // TODO: Maybe add in custom taxes based on shipping address province/postal code
        // Calculate grand total (sub total + tax (0.13) and shipping costs)
        po.setGrandTotal(calcGrandTotal(po.getSubTotal(), p.getShippingInfo().getPrice(), new BigDecimal(0.13)));

        // Create new purchase order with status "ORDERED"
        insertPurchaseOrder(po);

        po.setId(getLastInsertId());

        // Insert Purchase Order Item(s) using the CD ID and Order ID
        for (PoItem poItem : p.getPoItems()) {
            poItem.setPoId(po.getId());
            insertPoItem(poItem);
        }

        return po;
    }

    private BigDecimal calcSubtotal(List<PoItem> orderedItems) {
        BigDecimal subTotal = new BigDecimal(0);
        for (PoItem orderedItem : orderedItems) {
            subTotal = subTotal.add(orderedItem.getTotalPrice());
        }
        return subTotal;
    }

    private BigDecimal calcGrandTotal(BigDecimal subTotal, BigDecimal shippingCost, BigDecimal tax) {
        return new BigDecimal(0).add(subTotal).add(shippingCost).multiply(new BigDecimal(1).add(tax));
    }

    @Override
    public boolean confirmOrder(long orderId, boolean isAuthorized, List<Address> addressList) {

        if (addressList.size() < 2) {
            throw new RestDaoException("Please provide a shipping and billing address.");
        }

        // Update PO table with either PROCESSED or DENIED
        updatePurchaseOrderStatus(isAuthorized ? PoStatus.PROCESSED : PoStatus.DENIED, orderId);

        // Get existing order information
        PurchaseOrder existingOrder = getPurchaseOrderById(orderId);

        // Update the Customer's shipping & billing address for existing order
        for (Address a : addressList) {
            if (a.isShippingAddress()) {
                updateAddressById(a, existingOrder.getShippingAddressId());
            } else {
                updateAddressById(a, existingOrder.getBillingAddressId());
            }
        }

        return isAuthorized;
    }

    private long getLastInsertId() {
        return DataUtils.getLastInsertIdFromResult(dataAgent.getQueryResult(SQL_SELECT_LAST_INSERT_ID, new Object[]{}));
    }

    private void updateAddressById(Address a, long id) {
        dataAgent.executeSQL(SQL_UPDATE_ADDRESS_BY_ID, new Object[]{a.getAddressLine1(), a.getAddressLine2(), a.getCity(),
                a.getProvince(), a.getCountry(), a.getZip(), a.getPhone(), id});
    }

    private void updatePurchaseOrderStatus(PoStatus status, long id) {
        dataAgent.executeSQL(SQL_UPDATE_ORDER_STATUS, new Object[]{status.toString(), id});
    }

    private PurchaseOrder getPurchaseOrderById(long id) {
        return DataUtils.getPurchaseOrderFromResult(dataAgent.getQueryResult(SQL_SELECT_ORDER_BY_ID, new Object[]{id}));
    }

    private void insertPurchaseOrder(PurchaseOrder po) {
        dataAgent.executeSQL(SQL_INSERT_ORDER, new Object[]{po.getCustomerId(), po.getShippingAddressId(),
                po.getBillingAddressId(), po.getShippingTypeId(), po.getDateString(), po.getSubTotal(), po.getGrandTotal()});
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

        if (p.getShippingInfo().getId() < 1) {
            throw new RestDaoException("Invalid shipping info ID. Please provide Shipping information.");
        }

        if (p.getCustomer().getId() < 1) {
            throw new RestDaoException("Invalid customer ID. Please provide Customer information.");
        }

        if (p.getBillingAddress().getId() < 1) {
            throw new RestDaoException("Invalid Billing address ID. Please provide a Billing address.");
        }

        if (p.getShippingAddress().getId() < 1) {
            throw new RestDaoException("Invalid Shipping address ID. Please provide a Shipping address.");
        }

    }

}
