package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class newPassword extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    EditText newEmail, newOTP, newPass;
    Button btnReset;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        progressBar = findViewById(R.id.newProgress);
        newEmail =findViewById(R.id.newEmail);
       newOTP =findViewById(R.id.newOTP);
        newPass =findViewById(R.id.newOTPassword);
        btnReset = findViewById(R.id.newResetButton);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = newEmail.getText().toString();
                String otp = newOTP.getText().toString();
                String nPassword = newPass.getText().toString();

                if (email.isEmpty() || otp.isEmpty() || nPassword.isEmpty()) {
                    Toast.makeText(newPassword.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(email, otp ,nPassword);
            }
        });

    }

    public void postData(String email, String otp, String nPassword) {
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

        RequestBody requestBody = new FormBody.Builder()
                .add("email", email)
                .add("otp", otp)
                .add("password_hash", nPassword)
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/setNewPassword.php")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(newPassword.this, "Network error occurred", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string().trim();

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    if (result.equalsIgnoreCase("Password reset successful.")) {
                        // Navigate to password reset screen
                        Intent intent = new Intent(newPassword.this, login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(newPassword.this, "Reset failed: " + result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}