package com.superpizza.menu;

import com.superpizza.inventory.InventoryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuItem
{
    public String name;
    public Double price;
    public String id;
    public String imageURL;
    public Integer quantity = 1;
    public boolean enabled = true;

    private List<InventoryItem> includedItems = new ArrayList<>();

    public MenuItem(){}
    public MenuItem(String id, String name, double price, String imageURL)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public List<InventoryItem> getIncludedItems()
    {
        return includedItems;
    }

    public void setIncludedItems(List<InventoryItem> includedItems)
    {
        for (InventoryItem item : includedItems)
        {
            if (!item.enabled)
            {
                enabled = false;
            }
        }
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

        if (!this.id.equals(((MenuItem) rhs).id) || !this.name.equals(((MenuItem) rhs).name) || !Objects.equals(this.price, ((MenuItem) rhs).price) || this.includedItems.size() != ((MenuItem) rhs).includedItems.size())
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
