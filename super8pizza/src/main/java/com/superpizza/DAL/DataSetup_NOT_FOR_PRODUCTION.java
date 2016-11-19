package com.superpizza.DAL;

import com.superpizza.inventory.InventoryItem;
import com.superpizza.inventory.InventoryOption;
import com.superpizza.inventory.InventoryRepository;
import com.superpizza.menu.MenuItem;
import com.superpizza.menu.MenuRepository;
import com.superpizza.ordering.Address;
import com.superpizza.ordering.Order;
import com.superpizza.ordering.OrderOption;
import com.superpizza.ordering.OrderRepository;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DataSetup_NOT_FOR_PRODUCTION
{
    public static void setupMenuItems()
    {
        Map<String, MenuItem> menuMap = new HashMap<>();
        UUID id = UUID.randomUUID();
        MenuItem  item = new MenuItem(id.toString(), "Super 8 Special Pizza", 15.00, "images/super 8 special.jpg");
        item.setIncludedItems(getRandomInventoryItems(5));
        menuMap.put(id.toString(), item);
        id = UUID.randomUUID();
        item = new MenuItem(id.toString(), "Cheese Pizza", 12.00, "images/cheese.jpg");
        item.setIncludedItems(getRandomInventoryItems(5));
        menuMap.put(id.toString(), item);
        id = UUID.randomUUID();
        item = new MenuItem(id.toString(), "Pepperoni Pizza", 13.00, "images/pepperoni.jpg");
        item.setIncludedItems(getRandomInventoryItems(5));
        menuMap.put(id.toString(), item);
        id = UUID.randomUUID();
        item = new MenuItem(id.toString(), "Anchovies Pizza", 13.00, "images/anchovies.jpg");
        item.setIncludedItems(getRandomInventoryItems(5));
        menuMap.put(id.toString(), item);
        id = UUID.randomUUID();
        item = new MenuItem(id.toString(), "Mushrooms Pizza", 13.00, "images/mushrooms.jpg");
        item.setIncludedItems(getRandomInventoryItems(5));
        menuMap.put(id.toString(), item);
        id = UUID.randomUUID();
        item = new MenuItem(id.toString(), "Sausage Pizza", 13.00, "images/sausage.jpg");
        item.setIncludedItems(getRandomInventoryItems(5));
        menuMap.put(id.toString(), item);
        id = UUID.randomUUID();
        item = new MenuItem(id.toString(), "Supreme Pizza", 14.00 , "images/supreme.jpg");
        item.setIncludedItems(getRandomInventoryItems(5));
        menuMap.put(id.toString(), item);


        MenuRepository repo = new MenuRepository();
        repo.saveMenu(menuMap);
    }

    public static void setupOrders()
    {
        int orderNumber = 0;

        Map<String, Order> orderMap = new HashMap<>();
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        Address address = new Address("street1", "MKE", "WI", 112233);
        List<MenuItem> items = getRandomOrderItems();

        Order order = new Order(orderId.toString(), userId.toString(), new Date(), new OrderOption(Order.OrderType.Delivery.getValue(), Order.OrderType.Delivery.toString()), new OrderOption(Order.PaymentMethod.Card.getValue(), Order.PaymentMethod.Card.toString()), address, items, "" + orderNumber, "cardnumber", new OrderOption(Order.OrderStatus.Placed.getValue(), Order.OrderStatus.Placed.toString()));
        orderMap.put(orderId.toString(), order);

        orderNumber += 1;
        orderId = UUID.randomUUID();
        userId = UUID.randomUUID();
        address = new Address("street2", "MKE", "WI", 114537);
        items = getRandomOrderItems();

        order = new Order(orderId.toString(), userId.toString(), new Date(), new OrderOption(Order.OrderType.Pickup.getValue(), Order.OrderType.Pickup.toString()), new OrderOption(Order.PaymentMethod.Cash.getValue(), Order.PaymentMethod.Cash.toString()), address, items, "" + orderNumber, "cardnumber", new OrderOption(Order.OrderStatus.Making.getValue(), Order.OrderStatus.Making.toString()));

        orderMap.put(orderId.toString(), order);

        orderNumber += 1;
        orderId = UUID.randomUUID();
        userId = UUID.randomUUID();
        address = new Address("street3", "MKE", "WI", 984537);
        items = getRandomOrderItems();
        order = new Order(orderId.toString(), userId.toString(), new Date(), new OrderOption(Order.OrderType.Pickup.getValue(), Order.OrderType.Pickup.toString()), new OrderOption(Order.PaymentMethod.Card.getValue(), Order.PaymentMethod.Card.toString()), address, items, "" + orderNumber, "cardnumber", new OrderOption(Order.OrderStatus.Making.getValue(), Order.OrderStatus.Making.toString()));

        orderMap.put(orderId.toString(), order);

        orderNumber += 1;
        orderId = UUID.randomUUID();
        userId = UUID.randomUUID();
        address = new Address("street1", "MKE", "WI", 126587);
        items = getRandomOrderItems();
        order = new Order(orderId.toString(), userId.toString(), new Date(), new OrderOption(Order.OrderType.Delivery.getValue(), Order.OrderType.Delivery.toString()), new OrderOption(Order.PaymentMethod.Cash.getValue(), Order.PaymentMethod.Cash.toString()), address, items, "" + orderNumber, "cardnumber", new OrderOption(Order.OrderStatus.Complete.getValue(), Order.OrderStatus.Complete.toString()));

        orderMap.put(orderId.toString(), order);

        OrderRepository repo = new OrderRepository();
        repo.saveOrders(orderMap);
    }

    public static void setupInventory()
    {
        Map<String, InventoryItem> itemMap = new HashMap<>();

        List<InventoryItem> items = getRandomInventoryItems(20);

        for (InventoryItem item : items)
        {
            itemMap.put(item.id, item);
        }

        InventoryRepository repo = new InventoryRepository();
        repo.saveInventory(itemMap);
    }

    private static List<InventoryItem> getRandomInventoryItems(int numItems)
    {
        List<InventoryItem> items = new ArrayList<>();
        UUID id = UUID.randomUUID();

        for (int i = 0; i < numItems; i++)
        {
            int count = ThreadLocalRandom.current().nextInt(10, 100);
            int ordinal = ThreadLocalRandom.current().nextInt(0, 4);
            InventoryItem.ItemType type = InventoryItem.ItemType.values()[ordinal];
            InventoryItem item = new InventoryItem(id.toString(), "item " + i, count, ThreadLocalRandom.current().nextDouble(1, 3), new InventoryOption(InventoryItem.ItemType.valueOf(type.toString()).getValue(), type.toString()));
            item.quantity = ThreadLocalRandom.current().nextInt(1, 4);

            items.add(item);
            id = UUID.randomUUID();
        }

        return items;
    }

    private static List<MenuItem> getRandomOrderItems()
    {
        List<MenuItem> items = new ArrayList<>();
        UUID id = UUID.randomUUID();

        int numMenuItems = ThreadLocalRandom.current().nextInt(1, 4);
        int numInventoryItems = ThreadLocalRandom.current().nextInt(1, 4);

        for (int i = 0; i < numMenuItems; i++)
        {
            MenuItem item = new MenuItem(id.toString(), "item + id", 0.0, "imageURL");
            item.quantity = ThreadLocalRandom.current().nextInt(1, 4);

            items.add(new MenuItem(id.toString(), "menu item " + i, ThreadLocalRandom.current().nextDouble(10, 30), "imageURL"));
            id = UUID.randomUUID();
        }

        return items;
    }
}
