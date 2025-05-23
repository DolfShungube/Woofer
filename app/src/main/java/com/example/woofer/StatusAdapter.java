package com.example.woofer;

import android.content.Context;
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
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder> {

    private List<Status> statusList;
    private Context context;
    public StatusAdapter(List<Status> statusList, Context context) {
        this.statusList = statusList;
        this.context = context;
    }

    public int getItemCount(){
        return statusList.size();
    }

    public Status getItem(int i){
        return statusList.get(i);
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent,  false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        Status status = statusList.get(position);

        holder.usernameText.setText(status.getUsername());
        holder.statusText.setText(status.getText());
        holder.timestampText.setText(status.getTimestamp());
        holder.upvoteCount.setText(String.valueOf(status.getUpvotes()));

        holder.upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addUpvotes = status.getUpvotes() + 1;
                status.setUpvotes(addUpvotes);
                holder.upvoteCount.setText((String.valueOf(addUpvotes)));
            }
        });
    }


}
