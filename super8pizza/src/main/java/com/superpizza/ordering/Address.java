package com.superpizza.ordering;

public class Address {

    public String street;
    public String city;
    public String state;
    public long zip;

    public Address() {}
    public Address(String street, String city, String state, long zip)
    {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}
