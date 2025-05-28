package com.example.woofer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences prefs = getActivity().getSharedPreferences("WooferPrefs", Context.MODE_PRIVATE);
        String savedUsername = prefs.getString("username", "Unknown");


        TextView username = view.findViewById(R.id.userName);
        LinearLayout friendsLayout = view.findViewById(R.id.friendsLayout);
        Button logout = view.findViewById(R.id.logoutButton);

        username.setText(savedUsername);

        username.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangeUsernameActivity.class);
            startActivity(intent);
        });

        friendsLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FriendsActivity.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
        return view;
    }
}