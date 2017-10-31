package ca.edu.uottawa.csi5380.database.agent.utils;

import ca.edu.uottawa.csi5380.model.*;
import ca.edu.uottawa.csi5380.model.builders.AddressBuilder;
import ca.edu.uottawa.csi5380.model.builders.CustomerBuilder;
import ca.edu.uottawa.csi5380.model.builders.PurchaseOrderBuilder;
import ca.edu.uottawa.csi5380.model.builders.ShippingInfoBuilder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class DataUtils {

    private DataUtils() {
    }

    public static Address getAddressFromResult(SqlRowSet r) {
        if (!r.first()) return null;
        return new AddressBuilder()
                .setId(r.getLong("id"))
                .setFullName(r.getString("full_name"))
                .setAddressLine1(r.getString("address_line_1"))
                .setAddressLine2(r.getString("address_line_2"))
                .setCity(r.getString("city"))
                .setProvince(r.getString("province"))
                .setCountry(r.getString("country"))
                .setZip(r.getString("zip"))
                .setPhone(r.getString("phone"))
                .setType(r.getString("type"))
                .createAddress();
    }

    public static Customer getCustomerFromResult(SqlRowSet r) {

        // Confirm Customer exists
        if (!r.first()) {
            return null;
        }
        return new CustomerBuilder()
                .setId(r.getLong("id"))
                .setFirstName(r.getString("first_name"))
                .setLastName(r.getString("last_name"))
                .setEmail(r.getString("email"))
                .setPassword(r.getString("password"))
                .setDefaultShippingAddressId(r.getLong("default_shipping_address_id"))
                .setDefaultBillingAddressId(r.getLong("default_billing_address_id"))
                .createCustomer();
    }

    public static ShippingInfo getShippingInfoByIdFromResult(SqlRowSet r) {
        if (!r.first()) return null;
        return new ShippingInfoBuilder()
                .setId(r.getLong("id"))
                .setCompany(r.getString("company"))
                .setType(r.getString("type"))
                .setPrice(r.getBigDecimal("price"))
                .createShippingInfo();
    }

    public static List<ShippingInfo> getShippingInfoFromResult(SqlRowSet r) {
        List<ShippingInfo> shippingInfoList = new ArrayList<>();
        while (r.next()) {
            shippingInfoList.add(
                    new ShippingInfoBuilder()
                            .setId(r.getLong("id"))
                            .setCompany(r.getString("company"))
                            .setType(r.getString("type"))
                            .setPrice(r.getBigDecimal("price"))
                            .createShippingInfo());
        }
        return shippingInfoList;
    }

    public static PurchaseOrder getPurchaseOrderFromResult(SqlRowSet r) {

        if (!r.first()) {
            return null;
        }

        return new PurchaseOrderBuilder()
                .setId(r.getLong("id"))
                .setCustomerId(r.getLong("customer_id"))
                .setShippingAddressId(r.getLong("shipping_address_id"))
                .setBillingAddressId(r.getLong("billing_address_id"))
                .setShippingTypeId(r.getLong("shipping_type_id"))
                .setDate(getInstantFromString(r.getString("date")))
                .setStatus(PoStatus.valueOf(r.getString("status")))
                .setSubTotal(r.getBigDecimal("sub_total"))
                .setGrandTotal(r.getBigDecimal("grand_total"))
                .setTaxTotal(r.getBigDecimal("tax_total"))
                .setShippingTotal(r.getBigDecimal("shipping_total"))
                .createPurchaseOrder();
    }

    public static long getLastInsertIdFromResult(SqlRowSet r) {
        if (!r.first()) return -1;
        return r.getLong("id");
    }

    /**
     * Convert a MySQL DATETIME object string to a java.time.Instant object.
     *
     * @param s DATETIME string in the follow format: yyyy-LL-dd HH:mm:ss.S
     * @return
     */
    private static Instant getInstantFromString(String s) {
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S")).toInstant(ZoneOffset.UTC);
    }

}
