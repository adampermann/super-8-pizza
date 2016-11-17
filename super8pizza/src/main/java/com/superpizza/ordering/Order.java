package com.superpizza.ordering;

import com.superpizza.inventory.OrderableItem;
import java.util.Date;
import java.util.List;

public class Order
{
    public String orderId;
    public String orderNumber;
    public String userId;
    public Date timestamp;
    public OrderOption orderType;
    public OrderOption paymentMethod;
    public String cardNumber;
    public Address address;
    public List<OrderableItem> orderItems;
    public OrderOption orderStatus;
    public double price = 0.0;

    public Order(){}

    public Order(String orderId, String userId, Date timestamp, OrderOption type, OrderOption payment, Address address, List<OrderableItem> items, String orderNumber, String cardnumber, OrderOption orderStatus)
    {
        this.orderId = orderId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.orderType = type;
        this.paymentMethod = payment;
        this.address = address;
        this.orderItems = items;
        this.orderNumber = orderNumber;
        this.cardNumber = cardnumber;
        this.orderStatus = orderStatus;
    }


    public enum OrderType {
        Delivery(1), Pickup(2);

        private final int id;
        OrderType(int id) { this.id = id; }
        public int getValue() { return id; }
    }
    public enum PaymentMethod {
        Cash(1), Card(2);

        private final int id;
        PaymentMethod(int id) { this.id = id; }
        public int getValue() { return id; }
    }
    public enum OrderStatus {
        Placed(1), Making(2), Ready(3), Complete(4);

        private final int id;
        OrderStatus(int id) { this.id = id; }
        public int getValue() { return id; }
    }
}
