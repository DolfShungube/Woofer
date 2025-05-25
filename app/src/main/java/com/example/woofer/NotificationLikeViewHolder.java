package com.example.woofer;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationLikeViewHolder extends RecyclerView.ViewHolder {

    TextView title, timestamp, message;
    Button viewStatus;
    public NotificationLikeViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.likeNotification);
        timestamp = itemView.findViewById(R.id.timestampNote);
        message = itemView.findViewById(R.id.notificationText);
        viewStatus = itemView.findViewById(R.id.viewStatus);
    }
}
