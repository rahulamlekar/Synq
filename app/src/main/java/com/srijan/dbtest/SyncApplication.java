package com.srijan.dbtest;

import android.app.Application;

import com.firebase.client.Firebase;

/**
* Initialize Firebase with the application context. This must happen before the client is used.
*
* @author mimming
* @since 12/17/14
*/
public class SyncApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}