package com.example.firebasechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.firebasechat.utils.MyPrefs;

public class SplashActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (MyPrefs.getInstance(getApplicationContext(), CommonConstants.SHARED_PREF).getBoolean("login") == true) {
                    Intent intent = new Intent(SplashActivity.this, ProfileActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }, 1000);

    }
}