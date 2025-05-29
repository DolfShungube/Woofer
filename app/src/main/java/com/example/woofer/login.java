package com.example.woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login extends AppCompatActivity {
    Button login, signUp;
    TextView forgotten;
    TextView userText, passwordText;
    EditText username,password;
    OkHttpClient client = new OkHttpClient();

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login_btn);
        forgotten = findViewById(R.id.forgotPassword);
        username= findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        signUp = findViewById(R.id.btnSignup);

        username.setText("");
        password.setText("");

        forgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, resetPassword.class);
                startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String passwordUser = password.getText().toString();
                postData(user, passwordUser);
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(login.this,register.class);
                startActivity(i);
            }
        });

    }
    public void postData(String username, String password){
        RequestBody requestBody =  new FormBody.Builder()
                .add("username", username)
                .add("password", password)  // adjust based on PHP server expectation
                .build();

        Request request = new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2744607/login.php")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("WooferDebug", "Login failed: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(login.this, "Network error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string().trim();
                Log.d("WooferDebug", "Login response: " + result);
                runOnUiThread(() -> {
                    if(result.toLowerCase().contains("login successful")) {
                        Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject json = new JSONObject(result);
                            if (json.getBoolean("login_successful")) {
                                int userId = json.getInt("user_id");
                                String username = json.getString("username");

                                SharedPreferences prefs = getSharedPreferences("WooferPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("is_logged_in", true);
                                editor.putInt("user_id", userId);
                                editor.putString("username", username);
                                editor.apply();

                                startActivity(new Intent(login.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(login.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("WooferDebug", "JSON error: " + e.getMessage());
                            Toast.makeText(login.this, "Invalid server response", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(login.this, "Login failed: " + result, Toast.LENGTH_SHORT).show();
                        userText.setText("");
                        passwordText.setText("");
                    }
                });
            }
        });
    }


}
