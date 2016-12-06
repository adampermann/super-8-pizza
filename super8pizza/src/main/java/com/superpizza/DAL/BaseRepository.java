package com.superpizza.DAL;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;
import java.util.concurrent.Semaphore;

public abstract class BaseRepository <K, V>
{
    private static final String FIREBASE_KEY_FILENAME = "super8pizza-35e9b2fcedf5.json";
    private static final String DATABASE_URL = "https://super8pizza-ab3c8.firebaseio.com/";
    private static final Logger log = LoggerFactory.getLogger(BaseRepository.class);

    private static FirebaseDatabase database;
    private DatabaseReference ref;
    private String collectionName;

    public BaseRepository(String collectionName) throws DatabaseException
    {
        if(database == null)
        {
            SetupAppConnecion();
        }

        DatabaseReference dbRef = database.getReference("data/");
        this.collectionName = collectionName;
        ref = dbRef.child("/" + collectionName + "/");
    }

    private void SetupAppConnecion()
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
    }

    protected void saveData(Map<K, V> data) throws InterruptedException
    {
        //make sure thread lasts long enough to complete firebase call
        final Semaphore semaphore = new Semaphore(0);

        DatabaseReference.CompletionListener callback = (databaseError, databaseReference) -> {

            semaphore.release();

            if (databaseError != null) {
                log.error("Data could not be saved: {}", databaseError.getMessage());
                throw new DatabaseException("Unable to save data to " + collectionName);
            } else {
                log.info("Data saved successfully to {}", ref.toString());
            }
        };

        ref.setValue(data, callback);

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            log.error("Exception during waiting on save thread: {}", e.getMessage());
            throw e;
        }
    }

    protected void registerListener(ValueEventListener listener)
    {
        ref.addValueEventListener(listener);
    }
}
