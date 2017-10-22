package ca.edu.uottawa.csi5380.database.agent.utils;

import ca.edu.uottawa.csi5380.model.*;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public final class DataUtils {

    private DataUtils() {
    }

    public static Address getAddressFromResult(SqlRowSet r) {
        if (!r.first()) return null;
        return new Address(r.getLong("id"),
                    r.getString("full_name"),
                    r.getString("address_line_1"),
                    r.getString("address_line_2"),
                    r.getString("city"),
                    r.getString("province"),
                    r.getString("country"),
                    r.getString("zip"),
                    r.getString("phone"),
                    r.getString("type")
            );
    }

    public static Customer getCustomerFromResult(SqlRowSet r) {

        // Confirm Customer exists
        if (!r.first()) {
            return null;
        }
        return new Customer(r.getLong("id"),
                r.getString("first_name"),
                r.getString("last_name"),
                r.getString("email"),
                new String(Base64.getDecoder().decode(r.getString("password"))),
                r.getLong("default_shipping_address_id"),
                r.getLong("default_billing_address_id"));
    }

    public static ShippingInfo getShippingInfoByIdFromResult(SqlRowSet r) {
        if (!r.first()) return null;
        return new ShippingInfo(
                r.getLong("id"),
                r.getString("company"),
                r.getString("type"),
                r.getBigDecimal("price"));

    }

    public static List<ShippingInfo> getShippingInfoFromResult(SqlRowSet r) {
        List<ShippingInfo> shippingInfoList = new ArrayList<>();
        while (r.next()) {
            shippingInfoList.add(new ShippingInfo(
                    r.getLong("id"),
                    r.getString("company"),
                    r.getString("type"),
                    r.getBigDecimal("price")));
        }
        return shippingInfoList;
    }

    public static PurchaseOrder getPurchaseOrderFromResult(SqlRowSet r) {

        if (!r.first()) {
            return null;
        }

        return new PurchaseOrder(r.getLong("id"),
                r.getLong("customer_id"),
                r.getLong("shipping_address_id"),
                r.getLong("billing_address_id"),
                r.getLong("shipping_type_id"),
                getInstantFromString(r.getString("date")),
                PoStatus.valueOf(r.getString("status")),
                r.getBigDecimal("sub_total"),
                r.getBigDecimal("grand_total"),
                r.getBigDecimal("tax_total"));
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
    public static Instant getInstantFromString(String s) {
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss.S")).toInstant(ZoneOffset.UTC);
    }

}
