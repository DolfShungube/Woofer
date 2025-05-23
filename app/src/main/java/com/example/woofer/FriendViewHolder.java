package com.example.woofer;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

public class FriendViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    Uri imageUri;
    ShapeableImageView profileImage;
    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.friendUsername);
        profileImage = itemView.findViewById(R.id.friend);
    }
}
