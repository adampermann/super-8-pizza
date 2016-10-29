package menu;

import inventory.InventoryItem;

import java.util.List;

public class MenuItem
{
    private String name;
    private double price;
    private List<InventoryItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MenuItem(){}

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}