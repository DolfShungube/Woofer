package com.example.wooferlogin2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class register extends AppCompatActivity {
    EditText reg_emailAddress, reg_username,reg_password,reg_CfmPassword,reg_fullnames;
    Button reg_btn;
    TextView reg_reEmail,reg_U,reg_f,reg_p, reg_cfmP;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_fullnames = findViewById(R.id.Register_Fullnames);
        reg_emailAddress = findViewById(R.id.Register_UsernameEmail);
        reg_username = findViewById(R.id.Register_username);
        reg_password = findViewById(R.id.Register_passwordUsername);
        reg_CfmPassword = findViewById(R.id.Register_ConfirmPasswordUsername);
        reg_btn = findViewById(R.id.Register_signUp_btn);

        reg_reEmail = (TextView)reg_emailAddress;
        reg_U = (TextView) reg_username;
        reg_f = (TextView) reg_fullnames;
        reg_p =(TextView) reg_password;
        reg_cfmP = (TextView) reg_CfmPassword;


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
                String email = reg_emailAddress.getText().toString();
                String user = reg_username.getText().toString();
                String password = reg_password.getText().toString();
                String cfmpassword = reg_CfmPassword.getText().toString();
                String full_name = reg_fullnames.getText().toString();

                if (email.isEmpty() || user.isEmpty() || password.isEmpty() || cfmpassword.isEmpty() ) {
                    Toast.makeText(register.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(cfmpassword)) {
                    Toast.makeText(register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with network request
                    postData(user, email, password, full_name);
                }


            }
        });

    }

    public void postData(String user,String email,String password, String full_name){
        RequestBody requestBody = new FormBody.Builder()
                .add("username",user)
                .add("email", email)
                .add("password_hash", password)
                .add("full_name", full_name).build();

            Request request = new Request.Builder().url("https://lamp.ms.wits.ac.za/home/s2744607/register.php").post(requestBody).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String result = response.body().string().trim();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (result.equalsIgnoreCase("success")) {
                                Toast.makeText(register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(register.this, login.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(register.this, "Ensure you entered all the fields", Toast.LENGTH_SHORT).show();
                                reg_reEmail.setText("");
                                reg_U.setText("");
                                reg_f.setText("");
                                reg_p.setText("");
                                reg_cfmP.setText("");
                            }


                        }
                    });
                }


            });

        };

    }

