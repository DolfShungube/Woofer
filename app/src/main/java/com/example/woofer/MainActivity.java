package com.example.woofer;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView homeBtn = findViewById(R.id.home);
    ImageView searchBtn = findViewById(R.id.search);
    ImageView notificationBtn = findViewById(R.id.notifications);
    ImageView profileBtn = findViewById(R.id.profile);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}