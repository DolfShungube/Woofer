package com.example.woofer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;

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

public class StatusDetailActivity extends AppCompatActivity {
    OkHttpClient client  = new OkHttpClient();


    private TextView usernameText, statusText, timestampText, upvoteCount;
    ShapeableImageView profileImage;
    ImageView upvoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_status_detail);

        usernameText = findViewById(R.id.usernameText);
        statusText = findViewById(R.id.statusText);
        timestampText = findViewById(R.id.timestampText);
        upvoteCount = findViewById(R.id.upvoteCount);
        profileImage = findViewById(R.id.profileImage);
        upvoteButton = findViewById(R.id.upvoteButton);

        int statusId = getIntent().getIntExtra("status_id", -1);
        if(statusId != -1){
            getStatus(statusId);
        }
    }

    private void getStatus(int statusId) {
        statusId = getIntent().getIntExtra("status_id", -1);
        String url = "https://lamp.ms.wits.ac.za/home/s2744607/view_status.php?status_id=" + statusId;
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
                runOnUiThread(new Runnable() {
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
            JSONObject status = new JSONObject(json);

            String username = status.getString("username");
            String text = status.getString("content");
            String timestamp = status.getString("timestamp");
            int upvotes = status.getInt("likes");

            usernameText.setText(username);
            statusText.setText(text);
            timestampText.setText(timestamp);
            upvoteCount.setText(String.valueOf(upvotes));

            profileImage.setImageResource(R.drawable.default_profile);
            upvoteButton.setImageResource(R.drawable.like);
            upvoteButton.setClickable(false);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}