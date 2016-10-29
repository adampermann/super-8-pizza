//package inventory;
//
//import DAL.BaseRepository;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.ValueEventListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Semaphore;
//
//public class InventoryRepository extends BaseRepository
//{
//    private static final Logger log = LoggerFactory.getLogger(InventoryRepository.class);
//    private Inventory inventory;
//    private static final String collectionName = "inventory";
//    private static List<InventorySubscriber> subscribers = new ArrayList<>();
//
//    public InventoryRepository() {
//        super(collectionName);
//    }
//
//    protected InventoryRepository(String test) {
//
//        super(test + "/" + collectionName);
//    }
//
//    private void registerListener() throws InterruptedException {
//        //wait for callback to get initial data, otherwise may get null pointer exception if try accessing data before read thread completes
//        final Semaphore semaphore = new Semaphore(0);
//
//        ValueEventListener listener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                inventory = dataSnapshot.getValue(Inventory.class);
//                log.info("got: {}", inventory);
//
//                semaphore.release();
//
//                for (InventorySubscriber subscriber : subscribers)
//                {
//                    subscriber.dataChanged(inventory);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                log.error("The read failed: {}", databaseError.getCode());
//            }
//        };
//
//        super.registerListener("items", listener);
//
//        semaphore.acquire();
//    }
//
//    public Inventory getInventory(InventorySubscriber subscriber) throws InterruptedException {
//
//        subscribers.add(subscriber);
//
//        if (inventory == null)
//        {
//            registerListener();
//        }
//        return inventory;
//    }
//
//    public boolean saveInventory(Inventory inventory)
//    {
//        Map<String, Inventory> ingredientsMap = new HashMap<>();
//        ingredientsMap.put("items", inventory);
//
//        try {
//            super.saveData(ingredientsMap);
//        } catch (InterruptedException e) {
//            return false;
//        }
//
//        return true;
//    }
//
//    public interface InventorySubscriber
//    {
//        void dataChanged(Inventory newData);
//    }
//}
