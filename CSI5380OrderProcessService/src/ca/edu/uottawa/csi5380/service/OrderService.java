package ca.edu.uottawa.csi5380.service;

import ca.edu.uottawa.csi5380.database.agent.order.OrderDao;
import ca.edu.uottawa.csi5380.database.agent.order.OrderDaoImpl;
import ca.edu.uottawa.csi5380.model.Address;
import ca.edu.uottawa.csi5380.model.PurchaseOrder;
import ca.edu.uottawa.csi5380.model.ShippingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService implements OrderDao {

    private final OrderDaoImpl orderDaoImpl;

    @Autowired
    public OrderService(OrderDaoImpl orderDaoImpl) {
        this.orderDaoImpl = orderDaoImpl;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShippingInfo> getShippingInfo() {
        return orderDaoImpl.getShippingInfo();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchaseOrder createOrder(PurchaseOrder po) {
        return orderDaoImpl.createOrder(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmOrder(long orderId, boolean isAuthorized, List<Address> addressList) {
        return orderDaoImpl.confirmOrder(orderId, isAuthorized, addressList);
    }

}
