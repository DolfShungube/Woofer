package com.example.woofer;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends);
        int currentUserId = 1;
        friendRecyclerView = findViewById(R.id.friendsRecyclerView);
        friendAdapter = new FriendAdapter(friendList,this, currentUserId);
        friendRecyclerView.setAdapter(friendAdapter);

        back = findViewById(R.id.imageBack);
        back.setOnClickListener(v -> {
            finish();
        });

        fetchFriends();
    }

    private void fetchFriends() {
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/fetch_friends.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    String responseBody = response.body().string();
                    @Override
                    public void run() {
                        processJSON(responseBody);
                    }
                });
            }
        });
    }
    public void processJSON(String json) {

        try {
            JSONArray all = new JSONArray(json);
            friendList.clear();
            for (int i = 0; i < all.length(); i++) {
                JSONObject friend = all.getJSONObject(i);
                String friendUsername = friend.getString("username");
                int friend_id = friend.getInt("id");
                boolean isFollowing = friend.getBoolean("is_following");


                friendList.add(new Friend(friend_id, friendUsername, R.drawable.default_profile, isFollowing));
            }
            friendAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


}