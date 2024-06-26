package com.example.android_harjoitukset.utils;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.rxjava3.RxDataStore;

public class DataStoreSingleton {
    private RxDataStore<Preferences> datastore;
    private static final DataStoreSingleton ourInstance = new DataStoreSingleton();

    public static DataStoreSingleton getInstance() {
        return ourInstance;
    }

    private DataStoreSingleton() {
    }

    public void setDataStore(RxDataStore<Preferences> datastore) {
        this.datastore = datastore;
    }

    public RxDataStore<Preferences> getDataStore() {
        return datastore;
    }
}
