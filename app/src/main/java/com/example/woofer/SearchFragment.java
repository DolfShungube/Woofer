package com.example.woofer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private ListView listView;
    private SearchUserListAdapter adapter;
    private List<SearchUser> users = new ArrayList<>();

    private OkHttpClient client = new OkHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_friends, container, false);

        searchInput = view.findViewById(R.id.edit_search_friend);
        listView = view.findViewById(R.id.list_friends);

        adapter = new SearchUserListAdapter(getContext(), R.layout.item_friend, users);
        listView.setAdapter(adapter);


        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fetchUser(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void fetchUser(String query) {
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "https://lamp.ms.wits.ac.za/home/s2744607/get_user.php?search=" + query;

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
                Log.d("SearchResponse", responseBody);
                requireActivity().runOnUiThread(() -> processJSON(responseBody));
            }
        });
    }

    public void processJSON(String json) {
        try {
            adapter = new SearchUserListAdapter(getContext(), R.layout.item_friend, users);
            listView.setAdapter(adapter);

            JSONArray all = new JSONArray(json);
            users.clear();
            for (int i = 0; i < all.length(); i++) {
                JSONObject user = all.getJSONObject(i);
                int userId = user.getInt("id");
                String username = user.getString("username");


                users.add(new SearchUser(username, R.drawable.default_profile, userId));
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
