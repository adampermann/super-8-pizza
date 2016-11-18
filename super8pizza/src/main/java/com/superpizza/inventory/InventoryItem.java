package com.superpizza.inventory;

import com.superpizza.ordering.OrderableItem;

import java.util.Objects;

public class InventoryItem extends OrderableItem
{
    public int count;
    public InventoryOption type;

    public InventoryItem() {}
    public InventoryItem(String id, String name, int count, Double price, InventoryOption type)
    {
        super(id, name, price, "");
        this.count = count;
        this.type = type;
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

    public enum ItemType {
        Crust(1), Cheese(2), Topping(3), Sauce(4);

        private final int id;
        ItemType(int id) { this.id = id; }
        public int getValue() { return id; }
    }
}
