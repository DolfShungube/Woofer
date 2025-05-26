package com.example.woofer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.UrlRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    OkHttpClient client = new OkHttpClient();
    private List<Notification> notificationList;
    private Context context;

    private static final int type_like = 0;
    private static final int type_request = 1;


    public NotificationAdapter(List<Notification> notificationList, Context context){
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == type_like){
            View view = LayoutInflater.from(context).inflate(R.layout.item_notification_like, parent, false);
            return new NotificationLikeViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_notification_request, parent, false);
            return new NotificationRequestViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Notification notification = notificationList.get(position);

        if(holder instanceof NotificationLikeViewHolder){
            NotificationLikeViewHolder likeViewHolder = (NotificationLikeViewHolder) holder;

            likeViewHolder. title.setText("Liked Status");
            likeViewHolder.timestamp.setText(notification.getTimestamp());
            likeViewHolder.message.setText(notification.getMessage());

            likeViewHolder.viewStatus.setOnClickListener(v -> {
                Integer statusId = notification.getStatusId();
                if (statusId != null) {
                    Intent intent = new Intent(context, StatusDetailActivity.class);
                    intent.putExtra("status_id", statusId);
                    context.startActivity(intent);
                }
        });
        }else if(holder instanceof NotificationRequestViewHolder){
            NotificationRequestViewHolder requestViewHolder = (NotificationRequestViewHolder) holder;

            requestViewHolder.title.setText("Friend Request");
            requestViewHolder.timestamp.setText(notification.getTimestamp());
            requestViewHolder.message.setText(notification.getMessage());

            requestViewHolder.acceptRequest.setOnClickListener(v -> {
                acceptFriend(notification.getSenderId());
            });
            requestViewHolder.declineRequest.setOnClickListener(v -> {
                declineFriend(notification.getSenderId());
            });
        }
    }
    private void acceptFriend(Integer followerId) {
        String userId = "1";
        RequestBody requestBody = new FormBody.Builder()
                .add("following_id", userId)
                .add("follower_id", String.valueOf(followerId))
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/accept_friend.php")
                .post(requestBody)
                .header("User-Agent", "Woofer")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ((Activity) context).runOnUiThread(() -> notifyDataSetChanged());
            }
        });
    }
    private void declineFriend(Integer followerId) {
        String userId = "1";
        RequestBody requestBody = new FormBody.Builder()
                .add("following_id", userId)
                .add("follower_id", String.valueOf(followerId))
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/decline_friend.php")
                .post(requestBody)
                .header("User-Agent", "Woofer")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ((Activity) context).runOnUiThread(() -> notifyDataSetChanged());
            }
        });
    }

    public int getNotType(int position){
        Notification notification = notificationList.get(position);
        if(notification.getType().equals("liked_status")){
            return type_like;
        }else{
            return type_request;
        }
    }
    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
