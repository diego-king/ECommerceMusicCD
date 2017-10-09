package entity;

public class CD {
	private final String id;
	private final String title;
	private final Integer price;
	private final String category;
	private final String img_url;
	
	public CD(String id, String title, Integer price, String category, String img_url) {
		this.id = id;
		this.title = title;
		this.price = price;
		this.category = category;
		this.img_url = img_url;
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

	public String getImgUrl() {
		return img_url;
	}
}
