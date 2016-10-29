//package inventory;

import menu.MenuItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//public class InventoryController implements InventoryRepository.InventorySubscriber {

//    private static InventoryRepository repo = new InventoryRepository();
//    private static Inventory inventory;
//
//    public InventoryController()
//    {
//        try {
//            inventory = repo.getInventory(this);
//        } catch (InterruptedException e) {
//            //todo
//            e.printStackTrace();
//        }
//    }
//
//    public void AddItem(InventoryItem item)
//    {
//        inventory.addItem(item);
//        repo.saveInventory(inventory);
//    }
//
//    @Override
//    public void dataChanged(Inventory newData)
//    {
//        inventory = newData;
//    }
//}
