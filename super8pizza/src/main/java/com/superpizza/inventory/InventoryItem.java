package com.superpizza.inventory;

import java.util.Objects;

public class InventoryItem extends OrderableItem
{
    private int count;
    private String type; //todo crust, cheese, topping, sauce

    public InventoryItem() {}
    public InventoryItem(String id, String name, int count)
    {
        super(id, name, 0.0, "");
        this.count = count;
    }
    public InventoryItem(String id, String name, Double price)
    {
        super(id, name, price, "");
        this.count = 0;
    }
    public InventoryItem(String id, String name, Double price, String imageURL)
    {
        super(id, name, price, imageURL);
        this.count = 0;
    }
    public InventoryItem(String id, String name, int count, Double price, String imageURL)
    {
        super(id, name, price, imageURL);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

        if (this.id != ((InventoryItem) rhs).id || this.name != ((InventoryItem) rhs).name || !Objects.equals(this.price, ((InventoryItem) rhs).price) || this.count != ((InventoryItem) rhs).count)
        {
            return false;
        }

        return true;
    }
}
