package com.superpizza.menu;

import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
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

//	@RequestMapping(value="/addToMenu/{name}/{price}", method = RequestMethod.POST)
//	public void addMenuItem(@PathVariable("name") String name, @RequestParam("price") double price)
//	{
//		UUID id = UUID.randomUUID();
//		//menu.put(id.toString(), new MenuItem(id.toString(), name, price));
//		repo.saveMenu(menu);
//	}

	@RequestMapping("/getMenu")
	public List<MenuItem> getMenu()
	{
		List<MenuItem> viewModel = new ArrayList<>();

		for (Map.Entry<String, MenuItem> entry : menu.entrySet())
		{
			viewModel.add(entry.getValue());
		}

		return viewModel;
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
		for (Map.Entry<String, MenuItem> entry : newData.entrySet())
		{
			try {
				menu.put(entry.getKey(), entry.getValue());
			} catch (NullPointerException e)
			{
				String wth = e.getMessage();
				boolean ajojf = 1 + 4 == 1;
			}

		}
	}
}
