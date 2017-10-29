package ca.edu.uottawa.csi5380.model;

import java.math.BigDecimal;

/**
 * Represents a CD that can be purchased in the store.
 *<p>
 * This class models the CD table in the database.
 * </p>
 * Use this as a data transfer object to transfer data from the
 * database to server to client, and vice-versa.
 *
 * @author Kenny Byrd
 */
public class Cd {

    // Member variables
    private String id;
    private String title;
    private String artist;
    private String year;
    private String description;
    private BigDecimal price;
    private String label;
    private CdCategory category;
    private String imgUrl;

    // Default Constructor
    public Cd() {
        this.id = "";
        this.title = "";
        this.artist = "";
        this.year = "";
        this.description = "";
        this.price = new BigDecimal(0);
        this.label = "";
        this.category = CdCategory.COUNTRY;
        this.imgUrl = "";
    }

    public Cd(String id, String title, String artist, String year, String description,
              BigDecimal price, String label, String category, String imgUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.description = description;
        this.price = price;
        this.label = label;
        this.category = CdCategory.valueOf(category);
        this.imgUrl = imgUrl;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public CdCategory getCategory() {
        return category;
    }

    public void setCategory(CdCategory category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Cd{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", year='" + year + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", label='" + label + '\'' +
                ", category=" + category +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

}
