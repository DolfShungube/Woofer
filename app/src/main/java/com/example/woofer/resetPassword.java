package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class resetPassword extends AppCompatActivity {

    EditText resetEmail;
    Button resetSend;
    OkHttpClient client = new OkHttpClient();
    ProgressBar progressBar;

    private static final String TAG = "resetPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetSend = findViewById(R.id.resetSend);
        resetEmail = findViewById(R.id.resetEmail);
        progressBar = findViewById(R.id.resetProgress);

        resetSend.setOnClickListener(view -> {
            String email = resetEmail.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(resetPassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }
            postData(email);
        });
    }

    public void postData(String email) {
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/requestotp.php")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(resetPassword.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string().trim();
                Log.d(TAG, "Server Response: " + result);  // <== Logs full server response

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    if (result.contains("OTP sent to email")) {
                        // Navigate to password reset screen
                        Intent intent = new Intent(resetPassword.this, newPassword.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d("OTP_Response", "Result: " + result);
                        Toast.makeText(resetPassword.this, "Reset failed: " + result, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
