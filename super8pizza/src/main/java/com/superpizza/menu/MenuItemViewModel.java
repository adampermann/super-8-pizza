package com.superpizza.menu;

import com.google.firebase.database.Exclude;

/**
 * Created by adampermann on 11/3/16.
 */
public class MenuItemViewModel {
    public String name;
    public Double price;
    public String id;
    public String imageURL;
    @Exclude
    public Integer quantity = 1;

    MenuItemViewModel(String id,  String name, double price, String imageURL)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }
}
