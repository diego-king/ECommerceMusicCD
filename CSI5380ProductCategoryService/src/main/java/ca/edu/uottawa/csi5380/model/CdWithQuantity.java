package ca.edu.uottawa.csi5380.model;

import java.math.BigDecimal;

public class CdWithQuantity extends Cd {

    private final int quantity;
    
    public CdWithQuantity(Cd cd, int quantity) {
    	super(cd.getId(), cd.getTitle(), cd.getArtist(), cd.getYear(), cd.getDescription(), cd.getPrice(), cd.getLabel(), cd.getCategory(), cd.getImgUrl());
    	this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cd{" +
                "id='" + this.getId() + '\'' +
                ", title='" + this.getTitle() + '\'' +
                ", artist='" + this.getArtist() + '\'' +
                ", year='" + this.getYear() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", price=" + this.getPrice() +
                ", label='" + this.getLabel() + '\'' +
                ", category=" + this.getCategory() +
                ", imgUrl='" + this.getImgUrl() + '\'' +
                ", quantity='" + this.getQuantity() + '\'' +
                '}';
    }

    public int getQuantity() {
    	return this.quantity;
    }
}
