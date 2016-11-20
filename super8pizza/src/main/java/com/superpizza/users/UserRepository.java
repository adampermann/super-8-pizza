package com.superpizza.users;

import com.superpizza.DAL.BaseRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.ValueEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class UserRepository extends BaseRepository
{
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private static List<UserSubscriber> subscribers = new ArrayList<>();
    private Map<String, User> users = null;
    private static String collectionName = "users";

    public UserRepository() throws DatabaseException {
        super(collectionName);
    }

    protected UserRepository(String test) {

        super(test + "/" + collectionName);
    }

    private void registerListener() throws InterruptedException {
        //wait for callback to get initial data, otherwise may get null pointer exception if try accessing data before read thread completes
        final Semaphore semaphore = new Semaphore(0);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                log.info("got: {}", dataSnapshot);

                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    String key = child.getKey();
                    User value = child.getValue(User.class);

                    users.put(key, value);
                }

                semaphore.release();

                for (UserSubscriber subscriber : subscribers)
                {
                    subscriber.dataChanged(users);
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

    public Map<String, User> getUsers(UserSubscriber subscriber) throws InterruptedException
    {
        subscribers.add(subscriber);

        if (users == null)
        {
            users = new HashMap<>();
            registerListener();
        }
        return users;
    }

    public boolean saveUsers(Map<String, User> userMap)
    {
        this.users = userMap;

        try {
            super.saveData(userMap);
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }

    public interface UserSubscriber
    {
        void dataChanged(Map<String, User> newData);
    }
}
