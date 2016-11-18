package com.superpizza.ordering;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class OrderController implements OrderRepository.OrderSubscriber
{
    private OrderRepository repo = null;
    private Map<String, Order> orders;
    private long orderNumber = 0;

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
        optionsList.add(new OrderOption(Order.OrderType.Delivery.getValue(), Order.OrderType.Delivery.toString()));
        optionsList.add(new OrderOption(Order.OrderType.Pickup.getValue(), Order.OrderType.Pickup.toString()));

        return optionsList;
    }

    @RequestMapping("/getPaymentOptions")
    public List<OrderOption> getPaymentOptionMapping()
    {
        List<OrderOption> optionsList = new ArrayList<>();
        optionsList.add(new OrderOption(Order.PaymentMethod.Card.getValue(), Order.PaymentMethod.Card.toString()));
        optionsList.add(new OrderOption(Order.PaymentMethod.Cash.getValue(), Order.PaymentMethod.Cash.toString()));

        return optionsList;
    }

    @RequestMapping("/getOrderStatusOptions")
    public List<OrderOption> getOrderStatusMappings()
    {
        List<OrderOption> optionsList = new ArrayList<>();
        optionsList.add(new OrderOption(Order.OrderStatus.Placed.getValue(), Order.OrderStatus.Placed.toString()));
        optionsList.add(new OrderOption(Order.OrderStatus.Making.getValue(), Order.OrderStatus.Making.toString()));
        optionsList.add(new OrderOption(Order.OrderStatus.Ready.getValue(), Order.OrderStatus.Ready.toString()));
        optionsList.add(new OrderOption(Order.OrderStatus.Complete.getValue(), Order.OrderStatus.Complete.toString()));

        return optionsList;
    }

    @RequestMapping("/getOpenOrders")
    public List<Order> getOpenOrders()
    {
        List<Order> openOrders = new ArrayList<>();

        Iterator it = orders.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            Order order = (Order) pair.getValue();
            if (order.orderStatus.equals(Order.OrderStatus.Complete))
            {
                continue;
            }

            openOrders.add((Order)pair.getValue());
        }

        return openOrders;
    }

    @RequestMapping("/getCompleteOrders")
    public List<Order> getCompleteOrders()
    {
        List<Order> completeOrders = new ArrayList<>();

        Iterator it = orders.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            Order order = (Order) pair.getValue();
            if (order.equals(Order.OrderStatus.Complete))
            {
                completeOrders.add((Order)pair.getValue());
            }
        }

        return completeOrders;
    }

    @RequestMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody Order order)
    {
        String orderId = UUID.randomUUID().toString();
        order.orderId = orderId;
        orderNumber++;
        order.orderNumber = "" + orderNumber;

        try
        {
            orders.put(orderId, order);
            repo.saveOrders(orders);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/setOrderStatus")
    public ResponseEntity<?> setOrderStatus(@RequestBody Order updatedOrder)
    {
        if(!orders.containsKey(updatedOrder))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try
        {
            Order current = orders.get(updatedOrder.orderId);
            current.orderStatus = updatedOrder.orderStatus;
            orders.put(updatedOrder.orderId, current);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

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
