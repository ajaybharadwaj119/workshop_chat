package com.example.firebasechat;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasechat.databinding.AdapterDetailChatBinding;
import com.example.firebasechat.utils.CommonFunctions;
import com.example.firebasechat.utils.MyPrefs;
import com.example.firebasechat.utils.UserData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.ViewHolder> {

    ArrayList<MessageModel> chatMessage = new ArrayList<>();
    Activity activity;
    String recId = "", userId = "", msgType = "";
    Context context;
    // FirebaseDatabase database;

    public AdapterMessage(ArrayList<MessageModel> chatMessage, Activity activity) {
        this.chatMessage = chatMessage;
        this.activity = activity;
    }

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;


    @NonNull
    @Override
    public AdapterMessage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterDetailChatBinding binding = AdapterDetailChatBinding.inflate(CommonFunctions.getAdapterInflater(parent), parent, false);
        return new AdapterMessage.ViewHolder(binding.getRoot(), binding);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterMessage.ViewHolder holder, int position) {

        MessageModel messageModel = chatMessage.get(position);
        AdapterDetailChatBinding binding = holder.binding;

        //./   database = FirebaseDatabase.getInstance();

        userId = MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).getString(UserData.KEY_USER_ID);
        String id = MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).getString(UserData.KEY_LIST_ID);

        Log.i("cehckimaeg", userId + "-" + id);
        Log.i("MesageModule", messageModel.getMessage());

        if (messageModel.getuId().equals(userId)) {

            binding.senderLayout.setVisibility(View.VISIBLE);
            binding.receiverLayout.setVisibility(GONE);
            binding.senderText.setText(messageModel.getMessage());
            String timestamp = messageModel.getTmStamp();
            Date timeD = new Date(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            String time = sdf.format(timeD);
            binding.senderTime.setText(time);

        } else {

            binding.senderLayout.setVisibility(GONE);
            binding.receiverLayout.setVisibility(View.VISIBLE);
            binding.recieverText.setText(messageModel.getMessage());
            String timestamp = messageModel.getTmStamp();
            Date timeD = new Date(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            String time = sdf.format(timeD);
            binding.recieverTime.setText(time);
        }

    }

    @Override
    public int getItemCount() {
        return chatMessage.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterDetailChatBinding binding;

        public ViewHolder(@NonNull View itemView, AdapterDetailChatBinding binding) {
            super(itemView);

            this.binding = binding;
        }
    }
}
