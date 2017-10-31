package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.Cd;

import java.math.BigDecimal;

public class CdBuilder {
    private String id = "";
    private String title = "";
    private String artist = "";
    private String year = "";
    private String description = "";
    private BigDecimal price = new BigDecimal(0);
    private String label = "";
    private String category = "";
    private String imgUrl = "";

    public CdBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CdBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public CdBuilder setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public CdBuilder setYear(String year) {
        this.year = year;
        return this;
    }

    public CdBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CdBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CdBuilder setLabel(String label) {
        this.label = label;
        return this;
    }

    public CdBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public CdBuilder setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public Cd createCd() {
        return new Cd(id, title, artist, year, description, price, label, category, imgUrl);
    }
}