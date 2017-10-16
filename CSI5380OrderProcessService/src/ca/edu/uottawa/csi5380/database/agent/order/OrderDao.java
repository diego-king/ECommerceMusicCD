package ca.edu.uottawa.csi5380.database.agent.order;

import ca.edu.uottawa.csi5380.model.Address;
import ca.edu.uottawa.csi5380.model.PurchaseOrder;
import ca.edu.uottawa.csi5380.model.ShippingInfo;

import java.util.List;

public interface OrderDao {

    List<ShippingInfo> getShippingInfo();
    PurchaseOrder createOrder(PurchaseOrder po);
    boolean confirmOrder(long orderId, boolean isAuthorized, List<Address> addressList);

}
