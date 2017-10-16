package ca.edu.uottawa.csi5380.model;

import java.math.BigDecimal;

public class PoItem {

    private long poId;
    private String cdId;
    private BigDecimal unitPrice;
    private int numOrdered;

    public PoItem() {
        this.poId = -1;
        this.cdId = "";
        this.unitPrice = new BigDecimal(0);
        this.numOrdered = 0;
    }

    public PoItem(String cdId, BigDecimal unitPrice, int numOrdered) {
        this.poId = -1;
        this.cdId = cdId;
        this.unitPrice = unitPrice;
        this.numOrdered = numOrdered;
    }

    public PoItem(long poId, String cdId, BigDecimal unitPrice, int numOrdered) {
        this.poId = poId;
        this.cdId = cdId;
        this.unitPrice = unitPrice;
        this.numOrdered = numOrdered;
    }

    public void setPoId(long poId) {
        this.poId = poId;
    }

    public void setCdId(String cdId) {
        this.cdId = cdId;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setNumOrdered(int numOrdered) {
        this.numOrdered = numOrdered;
    }

    public long getPoId() {
        return poId;
    }

    public String getCdId() {
        return cdId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getNumOrdered() {
        return numOrdered;
    }

    /**
     * Return the total cost of the PoItem. Unit price * number of units ordered.
     *
     * @return
     */
    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(new BigDecimal(numOrdered));
    }

    @Override
    public String toString() {
        return "PoItem{" +
                "poId=" + poId +
                ", cdId='" + cdId + '\'' +
                ", unitPrice=" + unitPrice +
                ", numOrdered=" + numOrdered +
                '}';
    }

}
