package com.superpizza.ordering;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.ValueEventListener;
import com.superpizza.DAL.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class OrderRepository extends BaseRepository
{
    private static final Logger log = LoggerFactory.getLogger(OrderRepository.class);
    private static List<OrderSubscriber> subscribers = new ArrayList<>();
    private Map<String, Order> orders = null;
    private static String collectionName = "orders";

    public OrderRepository() throws DatabaseException {
        super(collectionName);
    }

    protected OrderRepository(String test) {

        super(test + "/" + collectionName);
    }

    private void registerListener() throws InterruptedException {
        //wait for callback to get initial data, otherwise may get null pointer exception if try accessing data before read thread completes
        final Semaphore semaphore = new Semaphore(0);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                log.info("got: {}", dataSnapshot);

                //todo
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    String key = child.getKey();
                    Order value = child.getValue(Order.class);

                    orders.put(key, value);
                }

                semaphore.release();

                for (OrderSubscriber subscriber : subscribers)
                {
                    subscriber.dataChanged(orders);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                log.error("The read failed: {}", databaseError.getCode());
            }
        };

        super.registerListener(listener);

        semaphore.acquire();
    }

    public Map<String, Order> getOrders(OrderSubscriber subscriber) throws InterruptedException
    {
        subscribers.add(subscriber);

        if (orders == null)
        {
            orders = new HashMap<>();
            registerListener();
        }
        return orders;
    }

    public boolean saveOrders(Map<String, Order> orders)
    {
        this.orders = orders;

        try {
            super.saveData(orders);
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }

    public interface OrderSubscriber
    {
        void dataChanged(Map<String, Order> newData);
    }
}

