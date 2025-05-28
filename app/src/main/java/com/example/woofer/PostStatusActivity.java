package com.example.woofer;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostStatusActivity extends AppCompatActivity {
    @SuppressLint("WrongViewCast")
    EditText statusText ;
    Button postButton;
    Button back;

    OkHttpClient client = new OkHttpClient();
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_status);


        statusText = findViewById(R.id.statusText);
        postButton = findViewById(R.id.postButton);
        back = findViewById(R.id.backToUpdates);

        back.setOnClickListener(v -> finish());

        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String text = statusText.getText().toString().trim();
                if(!text.isEmpty()) {
                    postStatus(text);
                } else {
                    statusText.setError("Status cannot be empty");
                }
            }
        });
    }

    private void postStatus(String statusTxt) {
        SharedPreferences prefs = getSharedPreferences("WooferPrefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        RequestBody requestBody = new FormBody.Builder()
                .add("user_id", String.valueOf(userId))
                .add("content", statusTxt)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/poststatus.php")
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
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseBody);
                        int statusId = json.getInt("status_id");

                        runOnUiThread(() -> {
                            Intent intent = new Intent(PostStatusActivity.this, StatusDetailActivity.class);
                            intent.putExtra("status_id", statusId);
                            startActivity(intent);
                            finish();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> statusText.setError("Unexpected server response"));
                    }
                } else {
                    runOnUiThread(() -> statusText.setError("Failed to post status"));
                }
            }
        });
    }

}