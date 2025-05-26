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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder> {

    private List<Status> statusList;
    private Context context;
    OkHttpClient client = new OkHttpClient();
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
                updateVotes(status, holder);
            }
        });
    }

    private void updateVotes(Status status, StatusViewHolder holder){

        String userId = "1";

        RequestBody formBody = new FormBody.Builder()
                .add("user_id", userId)
                .add("status_id", String.valueOf(status.getStatusId()))
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/update_votes.php")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful() && responseBody.contains("Upvote added")) {
                    holder.itemView.post(new Runnable() {
                        @Override
                        public void run() {
                            int newUpvotes = status.getUpvotes() + 1;
                            status.setUpvotes(newUpvotes);
                            holder.upvoteCount.setText(String.valueOf(newUpvotes));
                        }
                    });
                }
            }
        });
    }

}
