package ca.edu.uottawa.csi5380.service;

import ca.edu.uottawa.csi5380.database.agent.order.OrderDao;
import ca.edu.uottawa.csi5380.database.agent.order.OrderDaoImpl;
import ca.edu.uottawa.csi5380.model.*;
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
    public long createOrder(PurchaseEntry p) {
        return orderDaoImpl.createOrder(p);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmOrder(long orderId, boolean isAuthorized, AddressInfo addressInfo) {
        return orderDaoImpl.confirmOrder(orderId, isAuthorized, addressInfo);
    }

}
