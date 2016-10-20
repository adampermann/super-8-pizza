package DAL;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Semaphore;

public abstract class BaseRepository
{

    private static final String FIREBASE_KEY_FILENAME = "super8pizza-35e9b2fcedf5.json";

    private static final Logger log = LoggerFactory.getLogger(BaseRepository.class);
    public static final String DATABASE_URL = "https://super8pizza-ab3c8.firebaseio.com/";

    private static FirebaseDatabase database;
    protected static DatabaseReference ref;

    public BaseRepository() throws DatabaseException
    {
        InputStream resourceInputStream = BaseRepository.class.getClass().getResourceAsStream(FIREBASE_KEY_FILENAME);

        if (resourceInputStream == null)
        {
            resourceInputStream = this.getClass().getClassLoader().getResourceAsStream(FIREBASE_KEY_FILENAME);
            if (resourceInputStream == null)
            {
                log.error("Could not find firebase key file.");

                throw new DatabaseException("Unable to load repository config file. Cannot access database");
            }
        }

        // Initialize the app with a service account, granting admin privileges
        FirebaseOptions options;
        options = new FirebaseOptions.Builder()
                .setDatabaseUrl(DATABASE_URL)
                .setServiceAccount(resourceInputStream)
                .build();

        FirebaseApp.initializeApp(options);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("data/");
    }


    public abstract <K, V> Map<K, V> getValuesToWrite();
    public abstract String getCollectionName();

    /**
     * Saves the supplied Map (key, valle) data to the repository.
     * Repository classes extending from BaseRepository should implement
     * the following hook methods:
     * - getValuesToWrite()
     * - getCollectionName()
     */
    public final void saveData() throws InterruptedException {
        DatabaseReference saveRef = ref.child("/" + getCollectionName() + "/");

        //make sure thread lasts long enough to complete firebase call
        final Semaphore semaphore = new Semaphore(0);

        DatabaseReference.CompletionListener callback = (databaseError, databaseReference) -> {

            semaphore.release();

            if (databaseError != null) {
                log.error("Data could not be saved: {}", databaseError.getMessage());
                throw new DatabaseException("Unable to save data to " + getCollectionName());
            } else {
                log.info("Data saved successfully to {}", saveRef.toString());

            }
        };

        saveRef.setValue(getValuesToWrite(), callback);

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            log.error("Exception during waiting on save thread: {}", e.getMessage());
            throw e;
        }
    }

}
