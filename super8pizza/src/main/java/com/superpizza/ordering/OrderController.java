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
    public List<OrderOption> getDeliveryOptionMapping()
    {
        List<OrderOption> optionsList = new ArrayList<>();
        optionsList.add(new OrderOption(Order.OrderType.Delivery.ordinal(), Order.OrderType.Delivery.toString()));
        optionsList.add(new OrderOption(Order.OrderType.Pickup.ordinal(), Order.OrderType.Pickup.toString()));

        return optionsList;
    }

    @RequestMapping("/getPaymentOptions")
    public List<OrderOption> getPaymentOptionMapping()
    {
        List<OrderOption> optionsList = new ArrayList<>();
        optionsList.add(new OrderOption(Order.PaymentMethod.Card.ordinal(), Order.PaymentMethod.Card.toString()));
        optionsList.add(new OrderOption(Order.PaymentMethod.Cash.ordinal(), Order.PaymentMethod.Cash.toString()));

        return optionsList;
    }

    @RequestMapping("/getOrderStatusOptions")
    public List<OrderOption> getOrderStatusMappings()
    {
        List<OrderOption> optionsList = new ArrayList<>();
        optionsList.add(new OrderOption(Order.OrderStatus.Placed.ordinal(), Order.OrderStatus.Placed.toString()));
        optionsList.add(new OrderOption(Order.OrderStatus.Making.ordinal(), Order.OrderStatus.Making.toString()));
        optionsList.add(new OrderOption(Order.OrderStatus.Ready.ordinal(), Order.OrderStatus.Ready.toString()));
        optionsList.add(new OrderOption(Order.OrderStatus.Complete.ordinal(), Order.OrderStatus.Complete.toString()));

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
