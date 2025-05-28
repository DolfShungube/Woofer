package com.example.woofer;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;


public class SearchUserViewHolder {
    TextView username;
    ShapeableImageView profileImage;

    public SearchUserViewHolder(View view) {
        username = view.findViewById(R.id.text_username);
        profileImage = view.findViewById(R.id.image_friend);
    }
}

