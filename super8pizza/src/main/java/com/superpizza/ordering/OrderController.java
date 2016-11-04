package com.superpizza.ordering;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


public class OrderController implements OrderRepository.OrderSubscriber
{
    private OrderRepository repo = null;
    private Map<String, Order> orders;

    public OrderController()
    {
        repo = new OrderRepository();

        getOrdersFromRepo();
    }

    public OrderController(OrderRepository repo)
    {
        this.repo = repo;

        getOrdersFromRepo();
    }

    @RequestMapping("/getDeliveryOptions")
    public List<Map.Entry<String,Integer>> getDeliveryOptionMapping()
    {
        List<Map.Entry<String,Integer>> optionsList = new ArrayList<>();
        optionsList.add(new AbstractMap.SimpleEntry<>(Order.OrderType.Delivery.toString(), Order.OrderType.Delivery.ordinal()));
        optionsList.add(new AbstractMap.SimpleEntry<>(Order.OrderType.Pickup.toString(), Order.OrderType.Pickup.ordinal()));

        return optionsList;
    }

    @RequestMapping("/getPaymentOptions")
    public List<Map.Entry<String,Integer>> getPaymentOptionMapping()
    {
        List<Map.Entry<String,Integer>> optionsList = new ArrayList<>();
        optionsList.add(new AbstractMap.SimpleEntry<>(Order.PaymentMethod.Card.toString(), Order.PaymentMethod.Card.ordinal()));
        optionsList.add(new AbstractMap.SimpleEntry<>(Order.PaymentMethod.Cash.toString(), Order.PaymentMethod.Cash.ordinal()));

        return optionsList;
    }

    @RequestMapping("/getOrderStatusOptions")
    public List<Map.Entry<String,Integer>> getOrderStatusMappings()
    {
        List<Map.Entry<String,Integer>> optionsList = new ArrayList<>();
        optionsList.add(new AbstractMap.SimpleEntry<>(Order.OrderStatus.Placed.toString(), Order.OrderStatus.Placed.ordinal()));
        optionsList.add(new AbstractMap.SimpleEntry<>(Order.OrderStatus.Making.toString(), Order.OrderStatus.Making.ordinal()));
        optionsList.add(new AbstractMap.SimpleEntry<>(Order.OrderStatus.Ready.toString(), Order.OrderStatus.Ready.ordinal()));
        optionsList.add(new AbstractMap.SimpleEntry<>(Order.OrderStatus.Complete.toString(), Order.OrderStatus.Complete.ordinal()));

        return optionsList;
    }

    @RequestMapping("/getOrders")
    public List<Order> getOpenOrders()
    {
        List<Order> openOrders = new ArrayList<>();

        Iterator it = orders.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            Order order = (Order) pair.getValue();
            if (order.getOrderStatus() == Order.OrderStatus.Complete )
            {
                continue;
            }

            openOrders.add((Order)pair.getValue());
        }

        return openOrders;
    }

    @RequestMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody Order order)
    {
        //return order number
        //todo
        if (true)
        {
            return new ResponseEntity<>(order.getOrderNumber(), HttpStatus.OK);
        }
        //todo
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/setOrderStatus")
    public ResponseEntity<?> setOrderStatus(@RequestBody Order updatedOrder)
    {
        //todo
        if (true)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        //todo
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    public ResponseEntity<Car> update(@RequestBody Car car) {
//    ...
//    }

    private void getOrdersFromRepo()
    {
        try {
            orders = repo.getOrders(this);
        } catch (InterruptedException e) {
            //todo
            e.printStackTrace();
        }
    }

    @Override
    public void dataChanged(Map<String, Order> newData)
    {
        Iterator it = newData.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            orders.put((String)pair.getKey(), (Order)pair.getValue());
        }
    }
}
