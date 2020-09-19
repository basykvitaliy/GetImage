package com.basyk.getimage;

import android.app.Application;

import com.basyk.getimage.service.ApiFactory;
import com.basyk.getimage.service.ApiService;

public class MyApp extends Application {
    public static ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
    }

    private void initRetrofit() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        apiService = apiFactory.getApiServices();
    }
}
