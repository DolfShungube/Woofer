package com.example.woofer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.Objects;

public class StatusViewHolder extends RecyclerView.ViewHolder {
    TextView usernameText, timestampText, statusText, upvoteCount;
    ImageView upvoteButton;
    ShapeableImageView profileImage;

    public StatusViewHolder(View view) {
        super(view);
        usernameText = itemView.findViewById(R.id.usernameText);
        timestampText = itemView.findViewById(R.id.timestampText);
        statusText = itemView.findViewById(R.id.statusText);
        upvoteCount= itemView.findViewById(R.id.upvoteCount);
        upvoteButton = itemView.findViewById(R.id.upvoteButton);
        profileImage = itemView.findViewById(R.id.profileImage);
    }
}
