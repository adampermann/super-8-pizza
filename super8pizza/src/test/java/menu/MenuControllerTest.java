package menu;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static junit.framework.TestCase.assertNotSame;
import static org.junit.Assert.assertEquals;


import org.junit.Test;

public class MenuControllerTest implements MenuRepository.MenuSubscriber
{
	private static MenuController menuController = new MenuController("test");

	@org.junit.AfterClass
	public static void cleanup()
	{
		Map<String, MenuItem> i = new HashMap<>();
	}

	@Test
	public void saveGetInventory_RandomData()
	{
		//try {
			//Map<String, MenuItem> expected = setupRandomData();
			//Inventory result = repo.getInventory(this);

			//assertEquals(expected, result);
			//assertNotSame(expected, result);

//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			assert(false);
//		}
	}

//	private Map<String, MenuItem> setupRandomData()
//	{
//		Map<String, MenuItem> m = new HashMap<>();
//		m.put("menuId1", new MenuItem("pizza1", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId2", new MenuItem("pizza2", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId3", new MenuItem("pizza3", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId4", new MenuItem("pizza4", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId5", new MenuItem("pizza5", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId6", new MenuItem("pizza6", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId7", new MenuItem("pizza7", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId8", new MenuItem("pizza8", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId9", new MenuItem("pizza9", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		m.put("menuId10", new MenuItem("pizza10", ThreadLocalRandom.current().nextDouble(0.01, 1000.1)));
//		repo.saveMenu(m);
//
//		return m;
//	}

	@Override
	public void dataChanged(Map<String, MenuItem> newData) {}
}
