package ca.edu.uottawa.csi5380.api;

import ca.edu.uottawa.csi5380.model.Address;
import ca.edu.uottawa.csi5380.model.PurchaseEntry;
import ca.edu.uottawa.csi5380.model.PurchaseOrder;
import ca.edu.uottawa.csi5380.model.ShippingInfo;
import ca.edu.uottawa.csi5380.service.AuthorizationService;
import ca.edu.uottawa.csi5380.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides REST services for order related queries.
 *
 * @author Kenny Byrd
 */
@RestController
@RequestMapping("/api/order")
@Api(value = "/api/order")
public class OrderRestController {

    private static final Logger LOGGER = LogManager.getLogger(OrderRestController.class);

    private final OrderService orderService;
    private final AuthorizationService authorizationService;

    @Autowired
    public OrderRestController(OrderService orderService, AuthorizationService authorizationService) {
        this.orderService = orderService;
        this.authorizationService = authorizationService;
    }

    @ApiOperation(value = "Get shipping information.",
            notes = "Gets shipping information such as shipping company, type, and price of shipping method.",
            response = List.class)
    @RequestMapping(value = "/shipping", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShippingInfo> getShippingInfo() {
        LOGGER.info("getShippingInfo() called.");
        return orderService.getShippingInfo();
    }

    @ApiOperation(value = "Creates a new purchase order.",
            notes = "Creates a purchase order including shipping, taxes, total amount due based on shopping cart info. " +
                    "The PurchaseEntry object must contain a Customer (with ID), Shipping and Billing addresses (with IDs) " +
                    "and a list of PO items they're ordering.",
            response = PurchaseOrder.class)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public PurchaseOrder createOrder(@RequestBody PurchaseEntry p) {
        LOGGER.info(String.format("createOrder() called with params %s", p.toString()));
        return orderService.createOrder(p);
    }

    @ApiOperation(value = "Authorizes or declines an order.",
            notes = "Authorizes or declines the given order based on payment info, and stores shipping info with order. " +
                    "Note: The addressList should contain (2) addresses - one for SHIPPING and one for BILLING.",
            response = boolean.class)
    @RequestMapping(value = "/confirm/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean confirmOrder(@PathVariable long id, @RequestBody List<Address> addressList) {
        // TODO: do we need credit card number here?
        LOGGER.info(String.format("confirmOrder() called with id %s and addressList %s", id, addressList.toString()));
        return orderService.confirmOrder(id, authorizationService.authorizeOrder(), addressList);
    }

}
