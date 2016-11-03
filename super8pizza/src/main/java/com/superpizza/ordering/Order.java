package com.superpizza.ordering;

import com.superpizza.inventory.InventoryItem;
import com.superpizza.inventory.OrderableItem;

import java.util.Date;
import java.util.Map;

public class Order
{
    private String orderId;
    private long orderNumber;
    private String userId;
    private Date timestamp;
    private OrderType orderType;
    private PaymentMethod paymentMethod;
    private Address address;
    private Map<String, Integer> orderItems;
    private OrderStatus orderStatus;

    public Order(){}

    public Order(String orderId, String userId, Date timestamp, OrderType type, PaymentMethod payment, Address address, Map<String, Integer> items)
    {
        this.orderId = orderId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.orderType = type;
        this.paymentMethod = payment;
        this.address = address;
        this.orderItems = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map<String, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<String, Integer> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderNumber() { return orderNumber; }

    public void setOrderNumber(long orderNumber) { this.orderNumber = orderNumber; }

    public enum OrderType { Delivery, Pickup }
    public enum PaymentMethod { Cash, Card }
    public enum OrderStatus { Placed, Making, Ready, Complete }
}
