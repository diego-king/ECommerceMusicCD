package ca.edu.uottawa.csi5380.model;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private Customer customer;
    private List<Address> addressList;

    public Account() {
        this.customer = new Customer();
        this.addressList = new ArrayList<>();
    }
    public Account(Customer customer, List<Address> addressList) {
        this.customer = customer;
        this.addressList = addressList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "Account{" +
                "customer=" + customer +
                ", addressList=" + addressList +
                '}';
    }

}
