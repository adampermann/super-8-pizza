package com.superpizza.DAL;

import com.superpizza.inventory.InventoryItem;
import com.superpizza.inventory.OrderableItem;
import com.superpizza.menu.MenuItem;
import com.superpizza.menu.MenuRepository;
import com.superpizza.ordering.Address;
import com.superpizza.ordering.Order;
import com.superpizza.ordering.OrderRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DataSetup_NOT_FOR_PRODUCTION
{
    public static void setupMenuItems()
    {
        Map<String, MenuItem> menuMap = new HashMap<>();
        UUID id = UUID.randomUUID();
        menuMap.put(id.toString(), new MenuItem(id.toString(), "Super 8 Special Pizza", 15.00, "images/super 8 special.jpg"));
        id = UUID.randomUUID();
        menuMap.put(id.toString(), new MenuItem(id.toString(), "Cheese Pizza", 12.00, "images/cheese.jpg"));
        id = UUID.randomUUID();
        menuMap.put(id.toString(), new MenuItem(id.toString(), "Pepperoni Pizza", 13.00, "images/pepperoni.jpg"));
        id = UUID.randomUUID();
        menuMap.put(id.toString(), new MenuItem(id.toString(), "Anchovies Pizza", 13.00, "images/anchovies.jpg"));
        id = UUID.randomUUID();
        menuMap.put(id.toString(), new MenuItem(id.toString(), "Mushrooms Pizza", 13.00, "images/mushrooms.jpg"));
        id = UUID.randomUUID();
        menuMap.put(id.toString(), new MenuItem(id.toString(), "Sausage Pizza", 13.00, "images/sausage.jpg"));
        id = UUID.randomUUID();
        menuMap.put(id.toString(), new MenuItem(id.toString(), "Supreme Pizza", 14.00 , "images/supreme.jpg"));


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
        Map<String, Integer> items = getRandomItems();
        Order order = new Order(userId.toString(), orderId.toString(), new Date(), Order.OrderType.Delivery, Order.PaymentMethod.Card, address, items);
        order.setOrderStatus(Order.OrderStatus.Placed);
        order.setOrderNumber(orderNumber);
        orderMap.put(orderId.toString(), order);

        orderNumber += 1;
        orderId = UUID.randomUUID();
        userId = UUID.randomUUID();
        address = new Address("street2", "MKE", "WI", 114537);
        items = getRandomItems();
        order = new Order(userId.toString(), orderId.toString(), new Date(), Order.OrderType.Pickup, Order.PaymentMethod.Cash, address, items);
        order.setOrderStatus(Order.OrderStatus.Making);
        order.setOrderNumber(orderNumber);
        orderMap.put(orderId.toString(), order);

        orderNumber += 1;
        orderId = UUID.randomUUID();
        userId = UUID.randomUUID();
        address = new Address("street3", "MKE", "WI", 984537);
        items = getRandomItems();
        order = new Order(userId.toString(), orderId.toString(), new Date(), Order.OrderType.Pickup, Order.PaymentMethod.Card, address, items);
        order.setOrderStatus(Order.OrderStatus.Making);
        order.setOrderNumber(orderNumber);
        orderMap.put(orderId.toString(), order);

        orderNumber += 1;
        orderId = UUID.randomUUID();
        userId = UUID.randomUUID();
        address = new Address("street1", "MKE", "WI", 126587);
        items = getRandomItems();
        order = new Order(userId.toString(), orderId.toString(), new Date(), Order.OrderType.Delivery, Order.PaymentMethod.Cash, address, items);
        order.setOrderStatus(Order.OrderStatus.Complete);
        order.setOrderNumber(orderNumber);
        orderMap.put(orderId.toString(), order);


        OrderRepository repo = new OrderRepository();
        repo.saveOrders(orderMap);
    }

    private static Map<String,Integer> getRandomItems()
    {
        Map<String, Integer> items = new HashMap<>();
        UUID id = UUID.randomUUID();

        int numMenuItems = ThreadLocalRandom.current().nextInt(1, 4);
        int numInventoryItems = ThreadLocalRandom.current().nextInt(1, 4);

        for (int i = 0; i < numMenuItems; i++)
        {
            items.put(id.toString(), ThreadLocalRandom.current().nextInt(1, 4));
            id = UUID.randomUUID();
        }

        for (int i = 0; i < numInventoryItems; i++)
        {
            items.put(id.toString(), ThreadLocalRandom.current().nextInt(1, 4));
            id = UUID.randomUUID();
        }

        return items;
    }
}
