package menu;

import org.junit.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MenuControllerTest
{
	private static MenuRepository repo;
	private static MenuController menuController;
	private static Map<String, MenuItem> menu = new HashMap<>();

	@BeforeClass
	public static void testSetup()
	{
		repo = new MenuRepository("test");

		UUID id = UUID.randomUUID();
		menu.put(id.toString(), new MenuItem(id, "item1", ThreadLocalRandom.current().nextDouble(0, 21)));
		id = UUID.randomUUID();
		menu.put(id.toString(), new MenuItem(id, "item2", ThreadLocalRandom.current().nextDouble(0, 21)));
		id = UUID.randomUUID();
		menu.put(id.toString(), new MenuItem(id, "item3", ThreadLocalRandom.current().nextDouble(0, 21)));
		id = UUID.randomUUID();
		menu.put(id.toString(), new MenuItem(id, "item4", ThreadLocalRandom.current().nextDouble(0, 21)));
		id = UUID.randomUUID();
		menu.put(id.toString(), new MenuItem(id, "item5", ThreadLocalRandom.current().nextDouble(0, 21)));
		repo.saveMenu(menu);

		menuController = new MenuController(repo);
	}

	@Test
	public void getInventory_RandomData()
	{
		Map<String, MenuItem> result = menuController.getMenu();
		assert(menu.equals(result));
	}

	@Test
	public void addItem_UpdatesMenu()
	{
		menuController.addItem("new item", ThreadLocalRandom.current().nextDouble(0, 21));
		Map<String, MenuItem> result = menuController.getMenu();
		assert(menu.equals(result));
	}

	@Test
	public void addItem_UpdatesMenu_Cloud()
	{
		menuController.addItem("new item", ThreadLocalRandom.current().nextDouble(0, 21));
		menuController = null;
		menuController = new MenuController(repo);
		Map<String, MenuItem> result = menuController.getMenu();

		assert(menu.equals(result));
	}
}
