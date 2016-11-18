package com.superpizza.inventory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class InventoryController implements InventoryRepository.InventorySubscriber
{
	private InventoryRepository repo = null;
	private Map<String, InventoryItem> inventory;

	public InventoryController()
	{
		repo = new InventoryRepository();

		getInventoryFromRepo();
	}

	public InventoryController(InventoryRepository repo)
	{
		this.repo = repo;

		getInventoryFromRepo();
	}

	public void updateInvenotyItemCount(String id, int newCount)
	{
		if (!inventory.containsKey(id))
		{
			return;
		}

		InventoryItem current = inventory.get(id);
		current.count = newCount;

		inventory.put(id, current);
		repo.saveInventory(inventory);
	}

	@RequestMapping("/addInventoryItem")
	public ResponseEntity<?> addInventoryItem(@RequestBody InventoryItem item)
	{
		for (Map.Entry<String, InventoryItem> entry : inventory.entrySet())
		{
			if (entry.getValue().name.equalsIgnoreCase(item.name))
			{
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}

		try
		{
			String id = UUID.randomUUID().toString();
			item.id = id;
			inventory.put(id, item);
			repo.saveInventory(inventory);

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping("/getInventory")
	public List<InventoryItem> getInventory()
	{
		List<InventoryItem> viewModel = new ArrayList<>();

		for (Map.Entry<String, InventoryItem> entry : inventory.entrySet())
		{
			viewModel.add(entry.getValue());
		}

		return viewModel;
	}

	@RequestMapping("/updateInventory")
	public ResponseEntity<?> updateInventory(@RequestBody List<InventoryItem> inventory)
	{
		Map<String, InventoryItem> tempInventory = new HashMap<>();

		for (InventoryItem item : inventory)
		{
			tempInventory.put(item.id, item);
		}

		try {
			repo.saveInventory(tempInventory);
			this.inventory = tempInventory;
		} catch (Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void getInventoryFromRepo()
	{
		try {
			inventory = repo.getInventory(this);
		} catch (InterruptedException e) {
			//todo
			e.printStackTrace();
		}
	}

	@Override
	public void dataChanged(Map<String, InventoryItem> newData)
	{
		for (Map.Entry<String, InventoryItem> entry : newData.entrySet())
		{
			inventory.put(entry.getKey(), entry.getValue());
		}
	}
}
