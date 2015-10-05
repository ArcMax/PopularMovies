package com.explore.archana.nanoproject.app;

import android.app.Application;

import com.explore.archana.nanoproject.rest.RestClient;

/**
 * Created by archana on 9/25/2015.
 */
public class App extends Application {
    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
    }

    public static RestClient getRestClient(){
        return restClient;
    }
}
