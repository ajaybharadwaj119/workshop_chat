package com.example.firebasechat;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasechat.databinding.AdapterChatListBinding;
import com.example.firebasechat.utils.CommonFunctions;
import com.example.firebasechat.utils.MyPrefs;
import com.example.firebasechat.utils.OnItemViewClickListener;
import com.example.firebasechat.utils.UserData;

import java.util.List;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.ViewHolder> {

    Activity activity;
    List<Users> list;
    OnItemViewClickListener onItemViewClickListener;


    public AdapterChat(Activity activity, List<Users> list, OnItemViewClickListener onItemViewClickListener) {
        this.activity = activity;
        this.list = list;
        this.onItemViewClickListener = onItemViewClickListener;
    }

    @NonNull
    @Override
    public AdapterChat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterChatListBinding binding = AdapterChatListBinding.inflate(CommonFunctions.getAdapterInflater(parent), parent, false);
        return new AdapterChat.ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChat.ViewHolder holder, int position) {

        Users users = list.get(position);
        holder.binding.tvUsername.setText(users.getName());
        //holder.binding.tvLastMessage.setText(users.getMessage());



        String firstName = users.getName();
        String id = users.getUserId();
        MyPrefs.getInstance(activity,CommonConstants.SHARED_PREF).putString(UserData.KEY_NAME,firstName);
        MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).putString(UserData.KEY_LIST_ID, id);
        String s = firstName.substring(0, 1);
        holder.binding.TextViewFirst.setText(s);
        holder.binding.profileImage.setImageResource(R.drawable.bg_chat_icon);

        holder.binding.chatuserlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClickListener.onClick(v, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterChatListBinding binding;

        public ViewHolder(@NonNull View itemView, AdapterChatListBinding binding) {
            super(itemView);
            this.binding = binding;
        }
    }
}
