package ca.edu.uottawa.csi5380.model.builders;

import ca.edu.uottawa.csi5380.model.Address;
import ca.edu.uottawa.csi5380.model.AddressType;
import org.apache.commons.lang3.StringUtils;

public class AddressBuilder {
    private String fullName = "";
    private String addressLine1 = "";
    private String addressLine2 = "";
    private String city = "";
    private String province = "";
    private String country = "";
    private String zip = "";
    private String phone = "";
    private AddressType type = AddressType.SHIPPING;
    private long id = -1;

    public AddressBuilder setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public AddressBuilder setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public AddressBuilder setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public AddressBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder setProvince(String province) {
        this.province = province;
        return this;
    }

    public AddressBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public AddressBuilder setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public AddressBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public AddressBuilder setType(AddressType type) {
        this.type = type;
        return this;
    }

    public AddressBuilder setType(String type) {
        this.type = StringUtils.isNumeric(type) ? AddressType.values()[Integer.parseInt(type)] : AddressType.valueOf(type);
        return this;
    }

    public AddressBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public Address createAddress() {
        return new Address(id, fullName, addressLine1, addressLine2, city, province, country, zip, phone, type);
    }
}