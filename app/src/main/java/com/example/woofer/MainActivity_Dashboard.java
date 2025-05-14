package com.example.woofer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity_Dashboard extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;




        });
        fetch Fetcher = new fetch();
        Friends friends= new Friends();
        String userID="1";
        String getFriendsUrl="https://lamp.ms.wits.ac.za/home/s2683067/getFriends.php?myid=";
        Fetcher.myUrlBuilder(getFriendsUrl+userID);

        ScrollView friendsScrollView= findViewById(R.id.FriendsScrollView);


       // Fetcher.myUrlBuilder(getFriendsUrl+userID);
        Fetcher.getData(MainActivity_Dashboard.this, new requestHandler() {
            @Override
            public void processResponse(String response) {

                friends.getChats(response);
                //works!!!
                //WILL CONTINUE HERE

            }
        });
    }
}