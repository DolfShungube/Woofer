package com.example.woofer;

import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    ImageView homeBtn;
    ImageView searchBtn ;
    ImageView notificationsBtn;
    ImageView profileBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeBtn = findViewById(R.id.home);
        searchBtn = findViewById(R.id.search);
        notificationsBtn = findViewById(R.id.notifications);
        profileBtn = findViewById(R.id.profile);

        homeBtn.setOnClickListener(v -> loadFragment(new UpdatesFragment()));
        searchBtn.setOnClickListener(v -> loadFragment(new SearchFragment()));
        notificationsBtn.setOnClickListener(v -> loadFragment(new NotificationsFragment()));
        profileBtn.setOnClickListener(v -> loadFragment(new ProfileFragment()));

        loadFragment(new UpdatesFragment());


    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}