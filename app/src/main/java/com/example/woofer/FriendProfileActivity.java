package com.example.woofer;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class FriendProfileActivity extends AppCompatActivity {
    private ImageView back;
    private ImageView profileImage;
    private TextView userName;
    private ImageView friendsIcon;
    private TextView friends;
    private Button follow;

    private OkHttpClient client = new OkHttpClient();

    private int currentUserId;
    private int friendUserId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_profile);


        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        currentUserId = prefs.getInt("user_id", 0);

        LinearLayout friendsLayout = findViewById(R.id.friendsFriends);
        back = findViewById(R.id.imageBack);
        profileImage = findViewById(R.id.profileImage);
        userName = findViewById(R.id.userName);
        friendsIcon = findViewById(R.id.friendsIcon);
        friends = findViewById(R.id.friendsLabel);
        follow = findViewById(R.id.followButton);


        friendUserId = getIntent().getIntExtra("friend_user_id", 0);
        String friendUsername = getIntent().getStringExtra("friend_username");

        userName.setText(friendUsername);
        profileImage.setImageResource(R.drawable.default_profile); // Replace with actual profile image if you have

        back.setOnClickListener(v -> finish());

        friendsLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, FriendsActivity.class);
            startActivity(intent);
        });


        checkFollowStatus();


        follow.setOnClickListener(v -> {
            if (follow.getText().toString().equalsIgnoreCase("Follow")) {
                sendFollowRequest();
            } else {
                sendUnfollowRequest();
            }
        });
    }

    private void checkFollowStatus() {
        String url = "https://lamp.ms.wits.ac.za/home/s2744607/check_follow.php?user_id_1=" + currentUserId + "&user_id_2=" + friendUserId;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(FriendProfileActivity.this, "Failed to check follow status", Toast.LENGTH_SHORT).show());
            }
            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(FriendProfileActivity.this, "Error checking follow status", Toast.LENGTH_SHORT).show());
                    return;
                }
                String resp = response.body().string();
                boolean isFollowing = resp.contains("\"following\":true");
                runOnUiThread(() -> follow.setText(isFollowing ? "Following" : "Follow"));
            }
        });
    }

    private void sendFollowRequest() {
        String url = "https://lamp.ms.wits.ac.za/home/s2744607/add_friend.php?user_id_1=" + currentUserId + "&user_id_2=" + friendUserId;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(FriendProfileActivity.this, "Failed to send follow request", Toast.LENGTH_SHORT).show());
            }
            @Override public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                if (resp.contains("\"status\":\"success\"")) {
                    runOnUiThread(() -> {
                        Toast.makeText(FriendProfileActivity.this, "Followed successfully", Toast.LENGTH_SHORT).show();
                        follow.setText("Following");
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(FriendProfileActivity.this, "Failed to follow", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void sendUnfollowRequest() {
        String url = "https://lamp.ms.wits.ac.za/home/s2744607/remove_friend.php?user_id_1=" + currentUserId + "&user_id_2=" + friendUserId;

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(FriendProfileActivity.this, "Failed to unfollow", Toast.LENGTH_SHORT).show());
            }
            @Override public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                if (resp.contains("\"status\":\"success\"")) {
                    runOnUiThread(() -> {
                        Toast.makeText(FriendProfileActivity.this, "Unfollowed successfully", Toast.LENGTH_SHORT).show();
                        follow.setText("Follow");
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(FriendProfileActivity.this, "Failed to unfollow", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
