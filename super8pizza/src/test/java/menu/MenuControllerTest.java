package menu;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.Mockito;

public class MenuControllerTest
{
	private static MenuController menuController = new MenuController();
	private static MenuRepository menuRepoMock = mock(MenuRepository.class);

	@BeforeClass
	private void testSetup()
	{
		menuController.setRepository(menuRepoMock);
		Mockito.doNothing().when(menuRepoMock.saveMenu(anyMap()));
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
}
