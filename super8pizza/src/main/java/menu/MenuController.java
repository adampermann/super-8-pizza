package menu;

import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@RestController
public class MenuController implements MenuRepository.MenuSubscriber
{
	private MenuRepository repo = null;
	private Map<String, MenuItem> menu;

	public MenuController()
	{
		repo = new MenuRepository();

		getMenuFromRepo();
	}

	public MenuController(MenuRepository repo)
	{
		this.repo = repo;

		getMenuFromRepo();
	}

	@RequestMapping(value="/addToMenu/{name}/{price}", method = RequestMethod.POST)
	public void addItem(@PathVariable("name") String name, @RequestParam("price") double price)
	{
		UUID id = UUID.randomUUID();
		menu.put(id.toString(), new MenuItem(id, name, price));
		repo.saveMenu(menu);
	}

	@RequestMapping(value="/getMenu", method = RequestMethod.GET)
	public Map<String, MenuItem> getMenu()
	{
		return menu;
	}

	private void getMenuFromRepo()
	{
		try {
			menu = repo.getMenu(this);
		} catch (InterruptedException e) {
			//todo
			e.printStackTrace();
		}
	}

	@Override
	public void dataChanged(Map<String, MenuItem> newData)
	{
		Iterator it = newData.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();

			menu.put((String)pair.getKey(), (MenuItem)pair.getValue());
		}
	}
}
