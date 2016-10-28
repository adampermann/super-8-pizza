package inventory;

import menu.MenuItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController implements InventoryRepository.InventorySubscriber {

    private static InventoryRepository repo = new InventoryRepository();
    private static Inventory inventory;

    public InventoryController()
    {
        try {
            inventory = repo.getInventory(this);
        } catch (InterruptedException e) {
            //todo
            e.printStackTrace();
        }
    }

    public void AddItem(InventoryItem item)
    {
        inventory.addItem(item);
        repo.saveInventory(inventory);
    }

    public

    @GetMapping("/getMenu")
    public MenuItem[] getMenu() {
        MenuItem[] mockMenu = this.getMockMenu();
        return mockMenu;
    }

    private MenuItem[] getMockMenu() {
        MenuItem[] result = new MenuItem[5];
        result[0] = new MenuItem("Cheese Pizza", 11, 0);
        result[1] = new MenuItem("Pepperoni Pizza", 13, 0);
        result[2] = new MenuItem("Sausage Pizza", 13, 0);
        result[3] = new MenuItem("Supreme Pizza", 14, 0);
        result[4] = new MenuItem("Super 8 Special", 15, 0);

        return result;
    }

    @Override
    public void dataChanged(Inventory newData)
    {
        inventory = newData;
    }
}
