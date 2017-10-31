package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.PoItem;

import java.math.BigDecimal;

public class PoItemBuilder {
    private String cdId = "";
    private BigDecimal unitPrice = new BigDecimal(0);
    private int numOrdered = 0;
    private long poId = -1;

    public PoItemBuilder setCdId(String cdId) {
        this.cdId = cdId;
        return this;
    }

    public PoItemBuilder setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public PoItemBuilder setNumOrdered(int numOrdered) {
        this.numOrdered = numOrdered;
        return this;
    }

    public PoItemBuilder setPoId(long poId) {
        this.poId = poId;
        return this;
    }

    public PoItem createPoItem() {
        return new PoItem(poId, cdId, unitPrice, numOrdered);
    }
}