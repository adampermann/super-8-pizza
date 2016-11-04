package com.superpizza.menu;

/**
 * Created by adampermann on 11/3/16.
 */
public class MenuItemViewModel {
    public String name;
    public Double price;
    public String id;
    public String imageURL;
    public Integer quantity;

    MenuItemViewModel(String id,  String name, double price, String imageURL, int quantity)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.quantity = quantity;
    }
}
