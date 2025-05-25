package com.example.woofer;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationRequestViewHolder extends RecyclerView.ViewHolder {
    TextView title, timestamp, message;
    Button acceptRequest;
    Button declineRequest;
    public NotificationRequestViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.followRequest);
        timestamp = itemView.findViewById(R.id.timestampNote);
        message = itemView.findViewById(R.id.notificationTextFollow);
        acceptRequest = itemView.findViewById(R.id.acceptButton);
        declineRequest = itemView.findViewById(R.id.declineButton);

    }
}
