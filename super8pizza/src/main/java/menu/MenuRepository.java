package menu;

import DAL.BaseRepository;
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

public class MenuRepository extends BaseRepository
{
    private static final Logger log = LoggerFactory.getLogger(MenuRepository.class);
    private static List<MenuSubscriber> subscribers = new ArrayList<>();
    private Map<String, MenuItem> menu = null;
    private static String collectionName = "menu";

    public MenuRepository() throws DatabaseException {
        super(collectionName);
    }

    protected MenuRepository(String test) {

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
                    MenuItem value = child.getValue(MenuItem.class);

                    menu.put(key, value);
                }

                semaphore.release();

                for (MenuSubscriber subscriber : subscribers)
                {
                    subscriber.dataChanged(menu);
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

    public Map<String, MenuItem> getMenu(MenuSubscriber subscriber) throws InterruptedException
    {
        subscribers.add(subscriber);

        if (menu == null)
        {
            menu = new HashMap<>();
            registerListener();
        }
        return menu;
    }

    public boolean saveMenu(Map<String, MenuItem> menuMap)
    {
        this.menu = menuMap;

        try {
            super.saveData(menuMap);
        } catch (InterruptedException e) {
            return false;
        }

        return true;
    }

    public interface MenuSubscriber
    {
        void dataChanged(Map<String, MenuItem> newData);
    }
}
