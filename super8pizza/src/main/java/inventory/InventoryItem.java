package inventory;

public class InventoryItem
{
    public InventoryItem() {}
    public InventoryItem(String name, int count)
    {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String name;
    private int count;

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

        if (this.name != ((InventoryItem) rhs).name || this.count != ((InventoryItem) rhs).count)
        {
            return false;
        }

        return true;
    }
}
