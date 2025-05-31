package com.example.woofer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private OkHttpClient client = new OkHttpClient();

    private ActivityResultLauncher<Intent> postStatusLauncher;

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

        // Launch PostStatusActivity and refresh on return
        postStatusLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK) {
                        fetchStatuses();
                    }
                }
        );

        ImageView addStatus = view.findViewById(R.id.addText);
        addStatus.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PostStatusActivity.class);
            postStatusLauncher.launch(intent);
        });

        fetchStatuses();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchStatuses();
    }

    private void fetchStatuses() {
        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/getstatus.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                if (isAdded()) {  // Ensure fragment is attached
                    requireActivity().runOnUiThread(() -> processJSON(responseBody));
                }
            }
        });
    }

    public void processJSON(String json) {
        try {
            JSONArray all = new JSONArray(json);
            statusList.clear();
            for (int i = 0; i < all.length(); i++) {
                JSONObject status = all.getJSONObject(i);
                int statusId = status.getInt("status_id");
                String username = status.getString("username");
                String text = status.getString("content");
                String timestamp = status.getString("timestamp");
                int upvotes = status.getInt("likes");

                statusList.add(new Status(statusId, username, text, timestamp, R.drawable.default_profile, upvotes));
            }
            statusAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
