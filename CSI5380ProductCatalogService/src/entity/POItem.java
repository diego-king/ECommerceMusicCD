package entity;

public class POItem {
	private final int id;       
	private final String cdid;  
	private final int price;
	 
	public POItem (int id, String cdid, int price) {
		this.id = id;
		this.cdid = cdid;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	
	public String getCdid() {
		return cdid;
	}

	public int getPrice() {
		return price;
	}
}
