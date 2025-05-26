package com.example.woofer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FriendProfileActivity extends AppCompatActivity {
    private ImageView back;
    private ImageView profileImage;
    private TextView userName;
    private ImageView friendsIcon;
    private TextView friends;
    private Button follow;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friend_profile);

        Friend friend = (Friend) getIntent().getSerializableExtra("friend");

        back = findViewById(R.id.imageBack);
        profileImage = findViewById(R.id.profileImage);
        userName = findViewById(R.id.userName);
        friendsIcon = findViewById(R.id.friendsIcon);
        friends = findViewById(R.id.friendsLabel);
        follow = findViewById(R.id.followButton);

        if (friend != null) {
            profileImage.setImageResource(friend.getImageResId());
            userName.setText(friend.getFriendUsername());
            follow.setText(friend.isFollowing() ? "Following" : "Follow");
        }

        back.setOnClickListener(v -> finish());

    }
}