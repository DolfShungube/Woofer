package com.example.woofer;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import android.widget.Button;


public class FriendViewHolder extends RecyclerView.ViewHolder {
    TextView friendUsername;
    ShapeableImageView profileImage;

    Button followBtn;
    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);

        friendUsername = itemView.findViewById(R.id.friendUsername);
        profileImage = itemView.findViewById(R.id.friendProfileImage);
        followBtn = itemView.findViewById(R.id.followBtn);
    }
}
