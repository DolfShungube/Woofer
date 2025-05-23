package com.example.woofer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {
    private List<Friend> friendList;
    private Context context;

    public FriendAdapter(List<Friend> friendList, Context context) {
        this.friendList = friendList;
        this.context = context;
    }
    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend, parent,  false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friendList.get(position);

        holder.username.setText(friend.getUsername());
        
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }
}
