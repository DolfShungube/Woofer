package com.example.woofer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeUsernameActivity extends AppCompatActivity {
    private EditText changeUsername;
    private Button submitChange;
    private Button back;
    OkHttpClient client = new OkHttpClient();
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_username);

        changeUsername = findViewById(R.id.usernameChange);
        submitChange = findViewById(R.id.submitChange);
        back = findViewById(R.id.goBack);

        submitChange.setOnClickListener(v -> {
            String newUsername = changeUsername.getText().toString().trim();
            if (!newUsername.isEmpty()) {
                changeUsername(newUsername);
            } else {
                changeUsername.setError("Username cannot be empty");
            }
        });

        back.setOnClickListener(v -> finish());
    }

    private void changeUsername(String newUsername){
        RequestBody requestBody = new FormBody.Builder()
                .add("username", newUsername)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/update.php")
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
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            finish();
                        }
                    });
            }
        });
    }

}