package inventory;

import java.util.concurrent.ThreadLocalRandom;

import static junit.framework.TestCase.assertNotSame;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class InventoryTest implements InventoryRepository.InventorySubscriber
{
    private static InventoryRepository repo = new InventoryRepository("test");
    private static Inventory data;
    private boolean gotUpdate = false;

    @org.junit.AfterClass
    public static void cleanup()
    {
        Inventory i = new Inventory();
        i.setOnion(0);
        i.setGreenPeppers(0);
        i.setDough(0);
        i.setCheese(0);
        i.setBacon(0);
        i.setMushrooms(0);
        i.setPepperoni(0);
        i.setPineapple(0);
        i.setSauce(0);
        i.setSausage(0);
        repo.saveInventory(i);
    }

    @Test
    public void saveGetInventory_RandomData()
    {
        try {
            Inventory expected = setupRandomData();
            Inventory result = repo.getInventory(this);

            assertEquals(expected, result);
            assertNotSame(expected, result);

        } catch (InterruptedException e) {
            e.printStackTrace();
            assert(false);
        }
    }

    private Inventory setupRandomData() 
    {
        Inventory i = new Inventory();
        
        i.setOnion(ThreadLocalRandom.current().nextInt(0, 21));
        i.setGreenPeppers(ThreadLocalRandom.current().nextInt(0, 21));
        i.setDough(ThreadLocalRandom.current().nextInt(0, 21));
        i.setCheese(ThreadLocalRandom.current().nextInt(0, 21));
        i.setBacon(ThreadLocalRandom.current().nextInt(0, 21));
        i.setMushrooms(ThreadLocalRandom.current().nextInt(0, 21));
        i.setPepperoni(ThreadLocalRandom.current().nextInt(0, 21));
        i.setPineapple(ThreadLocalRandom.current().nextInt(0, 21));
        i.setSauce(ThreadLocalRandom.current().nextInt(0, 21));
        i.setSausage(ThreadLocalRandom.current().nextInt(0, 21));
        repo.saveInventory(i);

        return i;
    }

    @Override
    public void dataChanged(Inventory newData)
    {
        gotUpdate = true;
        data = newData;
    }
}
