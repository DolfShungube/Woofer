package com.example.woofer;

import android.view.View;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUserViewHolder {
    TextView username;
    CircleImageView profileImage;

    public SearchUserViewHolder(View view) {
        username = view.findViewById(R.id.text_username);
        profileImage = view.findViewById(R.id.image_friend);
    }
}

