package ca.edu.uottawa.csi5380.database.agent.order;

import ca.edu.uottawa.csi5380.model.*;

import java.util.List;

public interface OrderDao {

    List<ShippingInfo> getShippingInfo();
    PurchaseOrder createOrder(PurchaseEntry p);
    boolean confirmOrder(long orderId, boolean isAuthorized, AddressInfo addressInfo);

}
