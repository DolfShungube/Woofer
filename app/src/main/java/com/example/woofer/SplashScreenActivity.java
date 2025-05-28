package com.example.woofer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        int delayMillis = 3000;


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("WooferPrefs", MODE_PRIVATE);
                boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
                if(isLoggedIn){
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));

                }else {
                    startActivity(new Intent(SplashScreenActivity.this, login.class));

                }
                finish();
            }
        }, 3000);
    }
}

