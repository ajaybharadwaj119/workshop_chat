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
    public static Gson gson2 = new Gson();
    public SQLiteDatabase sqLiteDatabase;
    private static App app;
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
    public static Handler handler = new Handler(Looper.getMainLooper());
    // public static Gson gson = new Gson();
    public static IntentFilter intentFilter = new IntentFilter();
    public static boolean appInit = false;

//    String flurryKey = "8ZDFPNNK7JYQ8G95T4P6", flurryKeyLive="";

    public static App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public static OkHttpClient okHttpClient;
    public static long TIMEOUT = 60;
    public static MutableLiveData<Intent> intentLiveData = new MutableLiveData<>();
    public static MutableLiveData<Bundle> liveDataComment = new MutableLiveData<>(), liveDataChatLogin = new MutableLiveData<>(), liveDataSearch = new MutableLiveData<>();
    static final String APP_ID = "5918";
    static final String AUTH_KEY = "kUgSaFMFs7ez2OW";
    static final String AUTH_SECRET = "KWbt7V5ygcuFTVX";
    public static final String CON_PASSWORD = "Password@123";

    @Override
    public void onCreate() {
        super.onCreate();
        App.intentFilter.addAction("sendmessage");
        Log.i("AppState", "C");

        app = this;

        /*DataFormats.dateFormatTimeStampChat.setTimeZone(TimeZone.getTimeZone("GMT"));
        DataFormats.dateFormatDb.setTimeZone(TimeZone.getTimeZone("GMT"));
        DataFormats.dateFormatDbDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        DataFormats.dateFormatDbTime.setTimeZone(TimeZone.getTimeZone("GMT"));*/
        //TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
      //  myPrefs = new MyPrefs(getApplicationContext(), SHARED_PREF_NAME);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        /*for (String string : APPConstants.TRIP_STATUSES
        ) {
            intentFilter.addAction(string);
        }*/
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT * 5, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT * 5, TimeUnit.SECONDS)
                .build();
      //  AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
     //   ConnectycubeSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
     //   ConnectycubeSettings.getInstance().setAccountKey("");
       /* FreshchatConfig config = new FreshchatConfig("5a6070db-d8f0-41c6-bdbf-65221790e4bd","8e5c513e-2090-4247-88f9-cb642d28649b");
        config.setDomain("msdk.in.freshchat.com");
        config.setCameraCaptureEnabled(true);
        config.setGallerySelectionEnabled(true);
        config.setResponseExpectationEnabled(true);
        Freshchat.getInstance(getApplicationContext()).init(config);*/
     //   ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        //  new FlurryAgent.Builder().withLogEnabled(true).build(this, flurryKey);
        //TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    }

   /* @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onEnterForeground() {
        //run the code we need

        Log.i("AppState", "F");
        if (MyPrefs.getInstance().getBoolean("login")) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(BuildConfig.userspath).child(MyPrefs.getInstance().getString("userid"));
            reference.child("onlineStatus").setValue("online");
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onEnterBackground() {
        Log.i("AppState", "B");
        //run the code we need
        if (MyPrefs.getInstance().getBoolean("login")) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(BuildConfig.userspath).child(MyPrefs.getInstance().getString("userid"));
            reference.child("onlineStatus").setValue("offline");
            reference.child("lastSeen").setValue(DataFormats.dateFormatTimeStampChat.format(Calendar.getInstance().getTime()));
        }
    }*/

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        app = this;
    }

    public static boolean isAboveQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

}
