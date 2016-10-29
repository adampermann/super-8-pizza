//package inventory;
//
//import java.util.concurrent.ThreadLocalRandom;
//
//import static junit.framework.TestCase.assertNotSame;
//import static org.junit.Assert.assertEquals;
//import org.junit.Test;
//
//public class InventoryControllerTest implements InventoryRepository.InventorySubscriber
//{
//    private static InventoryRepository repo = new InventoryRepository("test");
//
//    @org.junit.AfterClass
//    public static void cleanup()
//    {
//        Inventory i = new Inventory();
//        repo.saveInventory(i);
//    }
//
//    @Test
//    public void saveGetInventory_RandomData()
//    {
//        try {
//            Inventory expected = setupRandomData();
//            Inventory result = repo.getInventory(this);
//
//            assertEquals(expected, result);
//            assertNotSame(expected, result);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            assert(false);
//        }
//    }
//
//    private Inventory setupRandomData()
//    {
//        Inventory i = new Inventory();
//        i.addItem(new InventoryItem("onion", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("greenPeppers", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("setDough", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("cheese", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("bacon", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("mushrooms", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("pepperoni", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("pineapple", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("sauce", ThreadLocalRandom.current().nextInt(0, 21)));
//        i.addItem(new InventoryItem("sausage", ThreadLocalRandom.current().nextInt(0, 21)));
//        repo.saveInventory(i);
//
//        return i;
//    }
//
//    @Override
//    public void dataChanged(Inventory newData) {}
//}
