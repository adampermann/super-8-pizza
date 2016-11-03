package com.superpizza.menu;

import com.superpizza.inventory.InventoryItem;
import com.superpizza.inventory.OrderableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MenuItem extends OrderableItem
{
    private List<InventoryItem> includedItems = new ArrayList<>();

    public MenuItem(){}

    public MenuItem(UUID id,  String name, double price) {
        super(id, name, price);
    }

    public List<InventoryItem> getIncludedItems()
    {
        return includedItems;
    }

    public void setIncludedItems(List<InventoryItem> includedItems)
    {
        this.includedItems = includedItems;
    }

    @Override
    public boolean equals(Object rhs)
    {
        if (rhs == null) {
            return false;
        }
        if (!MenuItem.class.isAssignableFrom(rhs.getClass())) {
            return false;
        }

        final MenuItem other = (MenuItem) rhs;
        if (other == null)
        {
            return false;
        }

        if (!this.id.equals(((MenuItem) rhs).id) || !this.name.equals(((MenuItem) rhs).name) || !Objects.equals(this.getPrice(), ((MenuItem) rhs).getPrice()) || this.includedItems.size() != ((MenuItem) rhs).includedItems.size())
        {
            return false;
        }

        for (int i = 0; i < includedItems.size(); i++)
        {
            if (!this.includedItems.get(i).equals(((MenuItem) rhs).includedItems.get(i)))
            {
                return false;
            }
        }

        return true;
    }
}
