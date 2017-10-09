package entity;

public class Address {
	final private int id;
	final private String street;
	final private String province;
	final private String country;
	final private String zip;
	final private String phone;
	
	public Address(int id, String street, String province, String country, String zip, String phone) {
		this.id = id;
		this.street = street;
		this.province = province;
		this.country = country;
		this.zip = zip;
		this.phone = phone;
	}
	
	public int getId() {
		return id;
	}

	public String getStreet() {
		return street;
	}

	public String getProvince() {
		return province;
	}

	public String getCountry() {
		return country;
	}

	public String getZip() {
		return zip;
	}

	public String getPhone() {
		return phone;
	}
}
