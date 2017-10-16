package ca.edu.uottawa.csi5380.database.agent.order;

import ca.edu.uottawa.csi5380.database.agent.DataAgent;
import ca.edu.uottawa.csi5380.database.agent.utils.DataUtils;
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
    private static final String SQL_SELECT_ORDER_BY_ID = "sql.select.order.by.id";

    // SQL INSERT KEYS
    private static final String SQL_INSERT_ORDER = "sql.insert.order";
    private static final String SQL_INSERT_PO_ITEM = "sql.insert.poitem";

    // SQL UPDATE KEYS
    private static final String SQL_UPDATE_ORDER_STATUS = "sql.update.order";
    private static final String SQL_UPDATE_ADDRESS = "sql.update.address";
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
    public PurchaseOrder createOrder(PurchaseOrder po) {

        // Set customer ID
        if (po.getCustomerId() <= 0) {
            po.setCustomerId(po.getCustomer().getId());
        }

        // Set Shipping Address ID
        if (po.getShippingAddressId() <= 0) {
            for (Address a: po.getAddressList()) {
                if (a.getType().equals(AddressType.SHIPPING)) {
                    po.setShippingAddressId(a.getId());
                }
            }
        }

        // Set Billing Address ID
        if (po.getBillingAddressId() <= 0) {
            for (Address a: po.getAddressList()) {
                if (a.getType().equals(AddressType.BILLING)) {
                    po.setBillingAddressId(a.getId());
                }
            }
        }

        // Set Shipping Type ID
        if (po.getShippingTypeId() <= 0) {
            po.setShippingTypeId(po.getShippingInfo().getId());
        }

        // Calculate sub total
        po.setSubTotal(calcSubtotal(po.getPoItems()));

        // Calculate grand total (sub total + tax (0.13) and shipping costs)
        // TODO: Maybe add in custom taxes based on billing/shipping address province
        po.setGrandTotal(calcGrandTotal(po.getSubTotal(), po.getShippingInfo().getPrice(), new BigDecimal(0.13)));

        // Create new purchase order with status "ORDERED"
        insertPurchaseOrder(po);

        po.setId(getLastInsertId());

        // Insert  PoItem using the CD ID and Order ID
        for (PoItem poItem : po.getPoItems()) {
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

        // Update PO table with either PROCESSED or DENIED
        updatePurchaseOrderStatus(isAuthorized ? PoStatus.PROCESSED : PoStatus.DENIED, orderId);

        // Update the Customer's shipping & billing address for this order
        PurchaseOrder existingOrder = getPurchaseOrderById(orderId);

        for (Address a: addressList) {
            if (a.getType().equals(AddressType.SHIPPING)) {
                updateAddressById(a, existingOrder.getShippingAddressId());
            } else {
                updateAddressById(a, existingOrder.getBillingAddressId());
            }
        }

        // This will also update the addresses for the customer, but they may not be the addresses
        // saved for this current order
        // addressList.forEach(this::updateAddress);

        return isAuthorized;
    }

    private long getLastInsertId() {
        return DataUtils.getLastInsertIdFromResult(dataAgent.getQueryResult(SQL_SELECT_LAST_INSERT_ID, new Object[]{}));
    }

    private void updateAddressById(Address a, long id) {
        dataAgent.executeSQL(SQL_UPDATE_ADDRESS_BY_ID, new Object[]{a.getAddressLine1(), a.getAddressLine2(), a.getCity(),
                a.getProvince(), a.getCountry(), a.getZip(), a.getPhone(), id});
    }

    private void updateAddress(Address a) {
        dataAgent.executeSQL(SQL_UPDATE_ADDRESS, new Object[]{a.getAddressLine1(), a.getAddressLine2(), a.getCity(),
                a.getProvince(), a.getCountry(), a.getZip(), a.getPhone(), a.getCustomerId(), a.getType().toString()});
    }

    private void updatePurchaseOrderStatus(PoStatus status, long id) {
        dataAgent.executeSQL(SQL_UPDATE_ORDER_STATUS, new Object[]{status.toString(), id});
    }

    private PurchaseOrder getPurchaseOrderById(long id) {
        return DataUtils.getPurchaseOrderFromResult(dataAgent.getQueryResult(SQL_SELECT_ORDER_BY_ID, new Object[] {id}));
    }

    private void insertPurchaseOrder(PurchaseOrder po) {
        dataAgent.executeSQL(SQL_INSERT_ORDER, new Object[]{po.getCustomerId(), po.getShippingAddressId(),
                po.getBillingAddressId(), po.getShippingTypeId(), po.getSubTotal(), po.getGrandTotal()});
    }

    private void insertPoItem(PoItem poItem) {
        dataAgent.executeSQL(SQL_INSERT_PO_ITEM, new Object[]{poItem.getPoId(), poItem.getCdId(), poItem.getUnitPrice(), poItem.getNumOrdered()});
    }

}
