package com.example.woofer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchUserListAdapter extends ArrayAdapter<SearchUser> {

    private Context context;
    private int resource;
    private List<SearchUser> originalList;
    private List<SearchUser> filteredList;
    private LayoutInflater inflater;

    public SearchUserListAdapter(Context context, int resource, List<SearchUser> friends) {
        super(context, resource, friends);
        this.context = context;
        this.resource = resource;
        this.originalList = new ArrayList<>(friends);
        this.filteredList = friends;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchUserViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            holder = new SearchUserViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SearchUserViewHolder) convertView.getTag();
        }

        SearchUser friend = getItem(position);
        holder.username.setText(friend.getUsername());

        convertView.setOnClickListener(v ->{
            Intent intent = new Intent(context, FriendProfileActivity.class);
            intent.putExtra("username", friend.getUsername());
            context.startActivity(intent);
        });

        return convertView;
    }

    public void filter(String query) {
        query = query.toLowerCase(Locale.getDefault());
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (SearchUser f : originalList) {
                if (f.getUsername().toLowerCase(Locale.getDefault()).contains(query)) {
                    filteredList.add(f);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public SearchUser getItem(int position) {
        return filteredList.get(position);
    }
}
