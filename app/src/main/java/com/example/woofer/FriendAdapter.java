package com.example.woofer;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {
    private List<Friend> friendList;
    private Context context;
    private int currentUserId;

    public FriendAdapter(List<Friend> friendList, Context context, int currentUserId) {
        this.friendList = friendList;
        this.context = context;
        this.currentUserId = currentUserId;
    }
    OkHttpClient client = new OkHttpClient();
    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_follow_friend, parent,  false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friendList.get(position);

        holder.friendUsername.setText(friend.getFriendUsername());
        if(friend.isFollowing()){
            holder.followBtn.setText("Following");
        }else{
            holder.followBtn.setText("Follow");
        }
        holder.profileImage.setImageResource(friend.getImageResId());

        holder.followBtn.setOnClickListener(v -> {
            if(friend.isFollowing()){
                unfollowRequest(currentUserId, friend.getId(), position);
            }else{
                followRequest(currentUserId, friend.getId(), position);
            }
            notifyItemChanged(position);
        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FriendProfileActivity.class);
            intent.putExtra("friend", friend);
            context.startActivity(intent);

        });

    }

    public void unfollowRequest(int userId, int friendId, int position){
        String url = "https://lamp.ms.wits.ac.za/home/s2744607/remove_friend.php" + "?user_id_1=" + userId + "&user_id_2=" + friendId;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        friendList.get(position).setFollowing(false);
                        notifyItemChanged(position);
                    }
                });
            }
        });

    }
    public void followRequest(int userId, int friendId, int position){
        String url = "https://lamp.ms.wits.ac.za/home/s2744607/add_friend.php" + "?user_id_1=" + userId + "&user_id_2=" + friendId;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        friendList.get(position).setFollowing(true);
                        notifyItemChanged(position);
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }
}
