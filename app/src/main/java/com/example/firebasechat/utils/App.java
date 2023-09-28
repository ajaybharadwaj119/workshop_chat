package com.example.firebasechat.utils;



import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class App extends Application implements LifecycleObserver {

    public static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    private static App app;

    public static IntentFilter intentFilter = new IntentFilter();


    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public static OkHttpClient okHttpClient;
    public static long TIMEOUT = 60;

    @Override
    public void onCreate() {
        super.onCreate();
        App.intentFilter.addAction("sendmessage");
        Log.i("AppState", "C");

        app = this;

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT * 5, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT * 5, TimeUnit.SECONDS)
                .build();

    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        app = this;
    }

    public static boolean isAboveQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

}
