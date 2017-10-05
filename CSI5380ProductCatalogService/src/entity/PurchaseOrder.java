package entity;

public class PurchaseOrder {
	private int id;       
	private String lastName; 
	private String firstName; 
	private String status; 
	private int addressId;
	 
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
