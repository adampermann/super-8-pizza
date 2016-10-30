package menu;

import inventory.InventoryItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MenuItemTest
{
	@Test
	public void TestFieldsSet()
	{
		UUID id= UUID.randomUUID();
		String name = "testItem";
		Double price = 11.11;

		MenuItem item = new MenuItem(id, name, price);

		assert(id.equals(item.getId()));
		assert(name.equals(item.getName()));
		assert(Objects.equals(price, item.getPrice()));
	}

	@Test
	public void TestEquals_SameObject()
	{
		UUID id= UUID.randomUUID();
		String name = "testItem";
		Double price = 11.11;

		MenuItem item = new MenuItem(id, name, price);

		CheckEqualityCase(item, item, true);
	}

	@Test
	public void TestEquals_DifferentFieldValues()
	{
		UUID id1= UUID.randomUUID();
		String name1 = "testItem1";
		Double price1 = 11.11;
		UUID id2= UUID.randomUUID();
		String name2 = "testItem2";
		Double price2 = 22.22;

		MenuItem item1 = new MenuItem(id1, name1, price1);
		MenuItem item2 = new MenuItem(id2, name2, price2);

		CheckEqualityCase(item1, item2, false);
	}

	@Test
	public void TestEquals_SameFieldValues_DifferentItems()
	{
		UUID id1= UUID.randomUUID();
		String name1 = "testItem1";
		Double price1 = 11.11;
		List<InventoryItem> items1 = new ArrayList<>();
		List<InventoryItem> items2 = new ArrayList<>();
		items1.add(new InventoryItem(UUID.randomUUID(), "item1", 1));
		items2.add(new InventoryItem(UUID.randomUUID(), "item2", 1));


		MenuItem item1 = new MenuItem(id1, name1, price1);
		MenuItem item2 = new MenuItem(id1, name1, price1);
		item1.setIncludedItems(items1);
		item2.setIncludedItems(items2);

		CheckEqualityCase(item1, item2, false);
	}

	@Test
	public void TestEquals_SameValues_SameItems()
	{
		UUID id1= UUID.randomUUID();
		String name1 = "testItem1";
		Double price1 = 11.11;
		List<InventoryItem> items1 = new ArrayList<>();
		items1.add(new InventoryItem(UUID.randomUUID(), "item1", 1));


		MenuItem item1 = new MenuItem(id1, name1, price1);
		MenuItem item2 = new MenuItem(id1, name1, price1);
		item1.setIncludedItems(items1);
		item2.setIncludedItems(items1);

		CheckEqualityCase(item1, item2, true);
	}

	private void CheckEqualityCase(MenuItem item1, MenuItem item2, boolean expected)
	{
		boolean result = item1.equals(item2);
		assert(expected == result);
	}
}
