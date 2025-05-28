package com.example.woofer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private ListView listView;
    private SearchUserListAdapter adapter;

    private List<SearchUser> dummyFriends() {
        List<SearchUser> list = new ArrayList<>();
        list.add(new SearchUser("wooferboy", "https://example.com/images/user1.jpg"));
        list.add(new SearchUser("happyhound", "https://example.com/images/user2.jpg"));
        list.add(new SearchUser("barkgal", "https://example.com/images/user3.jpg"));

        return list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_friends, container, false);

        searchInput = view.findViewById(R.id.edit_search_friend);
        listView = view.findViewById(R.id.list_friends);

        List<SearchUser> friends = dummyFriends(); // Replace with real data/API call
        adapter = new SearchUserListAdapter(getContext(), R.layout.item_friend, friends);
        listView.setAdapter(adapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }
}