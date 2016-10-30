package inventory;

import java.util.Objects;
import java.util.UUID;

public class InventoryItem extends OrderableItem
{
    private int count;

    public InventoryItem() {}
    public InventoryItem(UUID id, String name, int count)
    {
        super(id, name, 0.0);
        this.count = count;
    }
    public InventoryItem(UUID id, String name, Double price)
    {
        super(id, name, price);
        this.count = 0;
    }
    public InventoryItem(UUID id, String name, int count, Double price)
    {
        super(id, name, price);
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

        if (this.id != ((InventoryItem) rhs).id || this.name != ((InventoryItem) rhs).name || !Objects.equals(this.getPrice(), ((InventoryItem) rhs).getPrice()) || this.count != ((InventoryItem) rhs).count)
        {
            return false;
        }

        return true;
    }
}
