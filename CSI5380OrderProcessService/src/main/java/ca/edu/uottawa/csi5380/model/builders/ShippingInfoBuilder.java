package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.ShippingInfo;

import java.math.BigDecimal;

public class ShippingInfoBuilder {

    private long id = -1;
    private String company = "";
    private String type = "";
    private BigDecimal price = new BigDecimal(0);

    public ShippingInfoBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public ShippingInfoBuilder setCompany(String company) {
        this.company = company;
        return this;
    }

    public ShippingInfoBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public ShippingInfoBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ShippingInfo createShippingInfo() {
        return new ShippingInfo(id, company, type, price);
    }
}