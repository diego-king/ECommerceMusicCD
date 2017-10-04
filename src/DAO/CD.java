package DAO;

public class CD {
	final private String id;
	final private String title;
	final private Integer price;
	final private String category;
	public CD(String id, String title, Integer price, String category) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.category = category;
	}
	public String getId() {
		return this.id;
	}
	public String getTitle() {
		return this.title;
	}
	public Integer getPrice() {
		return this.price;
	}
	public String getCategory() {
		return this.category;
	}
}
