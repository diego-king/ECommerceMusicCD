package ca.edu.uottawa.csi5380.model;

import java.math.BigDecimal;

/**
 * Represents shipping information for a given
 * shipping method for the CD store.
 * <p>
 * It holds information about what the shipping company is:
 * (Canada Post, FedEx, UPS, Purolator, etc.), and the type of
 * shipping (regular, express, one-day), as well as the amount
 * of the shipping price.
 * <p>
 * This class maps directly to the ShippingInfo table in
 * the database.
 *
 * @author Kenny Byrd
 */
public class ShippingInfo {

    private long id;
    private String company;
    private String type;
    private BigDecimal price;

    public ShippingInfo() {
        this.id = 1;
        this.company = "Canada Post";
        this.type = "Regular";
        this.price = new BigDecimal(10);
    }

    public ShippingInfo(long id, String company, String type, BigDecimal price) {
        this.id = id;
        this.company = company;
        this.type = type;
        this.price = price;
    }

    // Getters & setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShippingInfo{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShippingInfo)) return false;

        ShippingInfo that = (ShippingInfo) o;

        if (getId() != that.getId()) return false;
        if (!getCompany().equals(that.getCompany())) return false;
        if (!getType().equals(that.getType())) return false;
        return getPrice().equals(that.getPrice());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getCompany().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getPrice().hashCode();
        return result;
    }

}
