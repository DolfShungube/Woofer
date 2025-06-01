package com.example.woofer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FriendsActivity extends AppCompatActivity {

    private RecyclerView friendRecyclerView;
    private FriendAdapter friendAdapter;
    private List<Friend> friendList = new ArrayList<>();
    private ImageView back;
    private OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        SharedPreferences prefs = getSharedPreferences("WooferPrefs", MODE_PRIVATE);
        int currentUserId = prefs.getInt("user_id", -1);


        friendRecyclerView = findViewById(R.id.friendsRecyclerView);
        friendAdapter = new FriendAdapter(friendList, this, currentUserId);
        friendRecyclerView.setAdapter(friendAdapter);


        back = findViewById(R.id.imageBack);
        back.setOnClickListener(v -> finish());


        fetchFriends();
    }

    private void fetchFriends() {
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/fetch_friends.php")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace(); // Optionally show a Toast on the UI thread
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    return; // Handle HTTP error if needed
                }

                String responseBody = response.body().string(); // Must be called only once
                runOnUiThread(() -> processJSON(responseBody));
            }
        });
    }

    private void processJSON(String json) {
        try {
            JSONArray all = new JSONArray(json);
            friendList.clear();

            for (int i = 0; i < all.length(); i++) {
                JSONObject friend = all.getJSONObject(i);
                String friendUsername = friend.getString("username");
                int friendId = friend.getInt("id");
                boolean isFollowing = friend.getBoolean("is_following");

                friendList.add(new Friend(friendId, friendUsername, R.drawable.default_profile, isFollowing));
            }

            friendAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace(); // Optionally show an error message
        }
    }
}
