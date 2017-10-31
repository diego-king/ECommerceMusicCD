package ca.edu.uottawa.csi5380.api;

import ca.edu.uottawa.csi5380.Application;
import ca.edu.uottawa.csi5380.model.AddressInfo;
import ca.edu.uottawa.csi5380.model.PurchaseEntry;
import ca.edu.uottawa.csi5380.model.ShippingInfo;
import ca.edu.uottawa.csi5380.model.builders.AddressInfoBuilder;
import ca.edu.uottawa.csi5380.model.builders.PoItemBuilder;
import ca.edu.uottawa.csi5380.model.builders.PurchaseEntryBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static ca.edu.uottawa.csi5380.constant.RestPaths.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for OrderRestController
 *
 * @author Kenny Byrd
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Transactional
public class OrderRestControllerTest {

    private JacksonTester<List<ShippingInfo>> shippingInfoTester;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Possibly configure the mapper
        JacksonTester.initFields(this, objectMapper);
    }

    /**
     * Test getShippingInfo() at URL: /api/order/shipping
     *
     * @throws Exception
     */
    @Test
    public void testGettingShippingInfo() throws Exception {

        String expected = "[{\"id\":1,\"company\":\"Canada Post\",\"type\":\"Priority\",\"price\":20.00},{\"id\":2,\"company\":\"Canada Post\"," +
                "\"type\":\"Xpresspost\",\"price\":15.00},{\"id\":3,\"company\":\"Canada Post\",\"type\":\"Regular\",\"price\":10.00}]";

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ORDER_BASE_REST_URL + ORDER_GET_SHIPPING_INFO_REST_URL);

        ResponseEntity<List<ShippingInfo>> resp = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ShippingInfo>>() {
                });
        List<ShippingInfo> result = resp.getBody();

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(shippingInfoTester.parseObject(expected)).isEqualTo(result);

    }

    /**
     * Test a successful creation of an order.
     * Specifically, testing createOrder() at URL: /api/order/create
     *
     */
    @Test
    public void testCreateOrder() {

        // Build path for creating order
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ORDER_BASE_REST_URL + ORDER_CREATE_REST_URL);

        PurchaseEntry purchaseEntry = new PurchaseEntryBuilder()
                .setCustomerEmail("andy_adler@gmail.com")
                .setShippingInfoId(1)
                .setPoItems(Collections.singletonList(
                        new PoItemBuilder()
                                .setCdId("cd001")
                                .setNumOrdered(1)
                                .setUnitPrice(new BigDecimal(15.99))
                                .createPoItem()))
                .createPurchaseEntry();

        ResponseEntity<Long> resp = restTemplate.postForEntity(builder.build().encode().toUri(), purchaseEntry, Long.class);
        Long orderId = resp.getBody();

        // Assert the Order response and ID are valid
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(orderId).isInstanceOf(Long.class);
        assertThat(orderId).isPositive();

    }

    /**
     * Test a successful creation and confirmation of an order.
     * Specifically, testing confirmOrder() at URL: /api/order/confirm
     *
     */
    @Test
    public void testConfirmOrder() {

        //*************************
        // STEP 1 - Create Order
        //*************************

        // Build path for creating order
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(ORDER_BASE_REST_URL + ORDER_CREATE_REST_URL);

        PurchaseEntry purchaseEntry = new PurchaseEntryBuilder()
                .setCustomerEmail("andy_adler@gmail.com")
                .setShippingInfoId(1)
                .setPoItems(Collections.singletonList(
                        new PoItemBuilder()
                                .setCdId("cd001")
                                .setNumOrdered(1)
                                .setUnitPrice(new BigDecimal(15.99))
                                .createPoItem()))
                .createPurchaseEntry();

        // Create a new Order first
        ResponseEntity<Long> resp = restTemplate.postForEntity(builder.build().encode().toUri(), purchaseEntry, Long.class);

        // Get the Order ID
        Long orderId = resp.getBody();

        // Assert the Order response and ID are valid
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(orderId).isInstanceOf(Long.class);
        assertThat(orderId).isPositive();

        //*************************
        // STEP 2 - Confirm Order
        //*************************

        // Build path for confirming order
        UriComponents url = UriComponentsBuilder.fromPath(ORDER_BASE_REST_URL + ORDER_CONFIRM_REST_URL)
                .queryParam("card", "4556220405738943")
                .buildAndExpand(orderId);

        // Create data
        AddressInfo addressInfo = new AddressInfoBuilder().createAddressInfo();

        // Confirm the order
        ResponseEntity<Boolean> confirmOrderResp = restTemplate.postForEntity(url.encode().toUri(), addressInfo, Boolean.class);
        Boolean orderConfirmed = confirmOrderResp.getBody();

        // Assert the Order response is valid
        assertThat(confirmOrderResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(orderConfirmed).isInstanceOf(Boolean.class);
        assertThat(orderConfirmed).isTrue();

    }

}
