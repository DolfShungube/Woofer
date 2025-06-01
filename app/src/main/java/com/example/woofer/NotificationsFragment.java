package com.example.woofer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class NotificationsFragment extends Fragment {

    private RecyclerView notificationRecyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList = new ArrayList<>();
    OkHttpClient client = new OkHttpClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
       notificationRecyclerView = view.findViewById(R.id.notificationsRecyclerView);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationAdapter = new NotificationAdapter(notificationList, getContext());
        notificationRecyclerView.setAdapter(notificationAdapter);

        fetchNotifications();
        return view;
    }

    public void onResume() {
        super.onResume();
        fetchNotifications();
    }
    private void fetchNotifications() {
        SharedPreferences prefs = getActivity().getSharedPreferences("WooferPrefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);
        String url = "https://lamp.ms.wits.ac.za/home/s2744607/get_notifications.php?user_id=" + userId;
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
                String responseBody = response.body().string();
                requireActivity().runOnUiThread(new Runnable() {
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
            notificationList.clear();
            for (int i = 0; i < all.length(); i++) {
                JSONObject notification = all.getJSONObject(i);
                String type = notification.getString("type");
                String senderUsername = notification.getString("senderUsername");
                String timestamp = notification.getString("timestamp");
                String message = notification.getString("message");
                int senderId = notification.optInt("senderId", -1);
                Integer statusId = notification.isNull("status_id") ? null : notification.getInt("status_id");

                if (statusId != null) {
                    notificationList.add(new Notification(type, senderUsername, timestamp, message, statusId, senderId));
                } else {
                    notificationList.add(new Notification(type, senderUsername, timestamp, message, null, senderId));
                }

            }
            notificationAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}