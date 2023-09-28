package com.example.firebasechat.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechat.R;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersViewHolder> {
    private List<User> userList;

    public MembersAdapter(List<User> userList) {
        this.userList = userList;
    }



    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_member, parent, false);
        return new MembersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MembersViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        public MembersViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}