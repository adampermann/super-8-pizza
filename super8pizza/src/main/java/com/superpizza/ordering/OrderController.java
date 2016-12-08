package com.superpizza.ordering;
import com.superpizza.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.awt.SystemColor.menu;

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

        for (Map.Entry<String, Order> entry : orders.entrySet())
        {
            if (entry.getValue().orderStatus.name.equals(Order.OrderStatus.Complete.toString()))
            {
                continue;
            }
            openOrders.add(entry.getValue());
        }

        return openOrders;
    }

    @RequestMapping("/getCompleteOrders")
    public List<Order> getCompleteOrders()
    {
        List<Order> completeOrders = new ArrayList<>();

        for (Map.Entry<String, Order> entry : orders.entrySet())
        {
            if (entry.getValue().orderStatus.name.equals(Order.OrderStatus.Complete.toString()))
            {
                completeOrders.add(entry.getValue());
            }
        }

        return completeOrders;
    }

    @RequestMapping("/getOrdersForUser/{userId}/")
    public List<Order> getOrdersForUser(@PathVariable String userId)
    {
        List<Order> userOrders = new ArrayList<>();

        for (Map.Entry<String, Order> entry : orders.entrySet())
        {
            if (entry.getValue().userId.equals(userId))
            {
                userOrders.add(entry.getValue());
            }
        }

        return userOrders;
    }

    @RequestMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody Order order)
    {
        String orderId = UUID.randomUUID().toString();
        order.orderId = orderId;
        order.timestamp = new Date();
        order.orderStatus = new OrderOption(Order.OrderStatus.Placed.getValue(), Order.OrderStatus.Placed.toString());
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
            return new ResponseEntity<>(new ResponseMessage("Error placing order, try again"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/setOrderStatus")
    public ResponseEntity<?> setOrderStatus(@RequestBody Order updatedOrder)
    {
        if(!orders.containsKey(updatedOrder.orderId))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try
        {
            Order current = orders.get(updatedOrder.orderId);
            current.orderStatus = updatedOrder.orderStatus;
            orders.put(updatedOrder.orderId, current);
            repo.saveOrders(orders);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Error setting order status"), HttpStatus.BAD_REQUEST);
        }
    }

    private void getOrdersFromRepo()
    {
        try {
            orders = repo.getOrders(this);

            for (Map.Entry orderEntry : orders.entrySet())
            {
                Order order = (Order)orderEntry.getValue();
                Long orderNumber = Long.parseLong(order.orderNumber);
                if (orderNumber > this.orderNumber)
                {
                    this.orderNumber = orderNumber;
                }
            }

        } catch (InterruptedException e) {
            //todo
            e.printStackTrace();
        }
    }

    @Override
    public void dataChanged(Map<String, Order> newData)
    {
        for (Map.Entry<String, Order> entry : newData.entrySet())
        {
            orders.put(entry.getKey(), entry.getValue());
        }
    }
}
