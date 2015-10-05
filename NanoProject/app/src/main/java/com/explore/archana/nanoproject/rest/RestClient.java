package com.explore.archana.nanoproject.rest;

import android.util.Log;

import com.explore.archana.nanoproject.rest.service.IApiCallService;
import com.explore.archana.nanoproject.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by archana on 9/24/2015.
 */
public class RestClient {

    private IApiCallService iApiCallService;

    public RestClient(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        iApiCallService = restAdapter.create(IApiCallService.class);
    }

    public IApiCallService getIApiCallService(){
        Log.e("response","response"+iApiCallService);
        return iApiCallService;
    }
}
