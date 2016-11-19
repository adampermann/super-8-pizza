package com.superpizza.inventory;

import java.util.Objects;

public class InventoryItem
{
    public InventoryOption type;
    public String name;
    public Double price;
    public String id;
    public boolean enabled = true;
    private static int disableItemThreshold = 10;
    private int numberInStock;
    public int quantity = 1;

    public InventoryItem() {}
    public InventoryItem(String id, String name, int numberInStock, Double price, InventoryOption type)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.setNumberInStock(numberInStock);
        this.type = type;
    }

    public int getNumberInStock()
    {
        return numberInStock;
    }

    public void setNumberInStock(int numberInStock)
    {
        this.numberInStock = numberInStock;
        if (numberInStock < disableItemThreshold)
        {
            enabled = false;
        }
    }

    @Override
    public boolean equals(Object rhs)
    {
        if (rhs == null) {
            return false;
        }
        if (!InventoryItem.class.isAssignableFrom(rhs.getClass())) {
            return false;
        }

        final InventoryItem other = (InventoryItem) rhs;
        if (other == null)
        {
            return false;
        }

        if (this.id != ((InventoryItem) rhs).id || this.name != ((InventoryItem) rhs).name || !Objects.equals(this.price, ((InventoryItem) rhs).price) || this.numberInStock != ((InventoryItem) rhs).numberInStock)
        {
            return false;
        }

        return true;
    }

    public enum ItemType {
        Crust(1), Cheese(2), Topping(3), Sauce(4);

        private final int id;
        ItemType(int id) { this.id = id; }
        public int getValue() { return id; }
    }
}
