package entity;

public class CD {
	private String id;
	private String title;
	private Integer price;
	private String category;
	private String img_url;
	
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
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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

	public void setImgUrl(String img_url) {
		this.img_url = img_url;
	}
	
}
