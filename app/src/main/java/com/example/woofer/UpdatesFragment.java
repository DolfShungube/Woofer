package com.example.woofer;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class UpdatesFragment extends Fragment {
    private RecyclerView statusRecyclerView;
    private StatusAdapter statusAdapter;
    private List<Status> statusList = new ArrayList<>();
    OkHttpClient client = new OkHttpClient();

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updates, container, false);
        statusRecyclerView = view.findViewById(R.id.statusRecyclerView);
        statusRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        statusAdapter = new StatusAdapter(statusList, getContext());
        statusRecyclerView.setAdapter(statusAdapter);

        fetchStatuses();
        return view;
    }

    private void fetchStatuses() {
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/ ")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                requireActivity().runOnUiThread(new Runnable() {
                    String responseBody = response.body().string();
                    @Override
                    public void run() {
                        processJSON(responseBody);
                    }
                });
            }
        });
    }

    public void processJSON(String json) {

        try {
            JSONArray all = new JSONArray(json);
            statusList.clear();
            for (int i = 0; i < all.length(); i++) {
                JSONObject status = all.getJSONObject(i);
                String username = status.getString("username");
                String text = status.getString("content");
                String timestamp = status.getString("timestamp");
                int upvotes = status.getInt("likes");

                statusList.add(new Status(username,text,timestamp,upvotes));
            }
            statusAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
