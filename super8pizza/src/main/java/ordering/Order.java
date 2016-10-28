package ordering;

import inventory.InventoryItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order
{
    private long orderId;
    private long userId;
    private Date timestamp;
    private OrderType orderType;
    private PaymentMethod paymentMethod;
    private Address address;
    private List<InventoryItem> orderItems = new ArrayList<>();
    private OrderStatus orderStatus;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public List<InventoryItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<InventoryItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getOrderStatud() {
        return orderStatus;
    }

    public void setOrderStatud(OrderStatus orderStatud) {
        this.orderStatus = orderStatud;
    }


    public enum OrderType { Delivery, Pickup}
    public enum PaymentMethod { Cash, Card }
    public enum OrderStatus { Placed, Making, Ready, Complete }
}
