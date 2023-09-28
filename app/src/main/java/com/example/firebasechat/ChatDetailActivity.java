package com.example.firebasechat;

import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.firebasechat.databinding.ActivityChatDetailBinding;
import com.example.firebasechat.utils.MyPrefs;
import com.example.firebasechat.utils.UserData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    Activity activity;
    FirebaseDatabase database;
    FirebaseAuth auth;
    public static String userid = "", type = "", name = "", receiverId = "";
    AdapterMessage chatAdapter;
    ArrayList<MessageModel> messageModels = new ArrayList<>();
    public static StorageReference storageRef;
    private FirebaseStorage storage;

    String senderRoom = "", recieverRoom = "";
    MyPrefs myPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        activity = this;

        database = FirebaseDatabase.getInstance("https://chat-firebase-cd16a-default-rtdb.firebaseio.com/");
        auth = FirebaseAuth.getInstance();


        userid = MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).getString(UserData.KEY_USER_ID);
        //  receiverId = MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).getString(UserData.KEY_LIST_ID);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            receiverId = bundle.getString("userID");
            Log.i("sendsssData1", userid + "/" + type);
        }


        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        layoutManger.setStackFromEnd(true);
        binding.RecycleViewChat.setLayoutManager(layoutManger);
        chatAdapter = new AdapterMessage(messageModels, activity);
        binding.RecycleViewChat.setAdapter(chatAdapter);

        int sendID, recID;
        sendID = Integer.parseInt(userid);
        recID = Integer.parseInt(receiverId);

        Log.i("SendRecId", sendID + " " + recID);

        if (sendID > recID) {
            senderRoom = receiverId + " - " + userid;
            recieverRoom = receiverId + " - " + userid;
        } else {
            senderRoom = userid + " - " + receiverId;
            recieverRoom = userid + " - " + receiverId;
        }


        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        database.getReference().child("chats/" + userid).child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                try {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        MessageModel model = snapshot1.getValue(MessageModel.class);
                        Log.i("modeldetails", model.getMessage());
                        model.setMessageId(snapshot1.getKey());
                        messageModels.add(model);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault());

        String currentDateandTime = sdf.format(new Date());
        currentDateandTime = currentDateandTime.replace("am", "AM").replace("pm", "PM");

        String finalCurrentDateandTime = currentDateandTime;


        binding.ImageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = binding.EditTextChat.getText().toString().trim();

                if (message.equalsIgnoreCase("")) {

                    Toast.makeText(activity, "You cannot send empty message", Toast.LENGTH_SHORT).show();

                } else {


                    DatabaseReference senderReference = database.getReference().child("chats").child(userid).child(senderRoom).push();
                    String messageId = senderReference.getKey();


                    database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            database.getReference().child("chats/" + MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).getString(UserData.KEY_USER_ID)).child(recieverRoom).child("userName").setValue(name);
                            database.getReference().child("chats/" + MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).getString(UserData.KEY_USER_ID)).child(recieverRoom).child("uid").setValue(receiverId);
                            database.getReference().child("chats/" + receiverId).child(recieverRoom).child("uid").setValue(userid);
                            database.getReference().child("chats/" + receiverId).child(recieverRoom).child("userName").setValue(name);

                            MessageModel model = new MessageModel(userid, message, "", "");
                            model.setTmStamp(finalCurrentDateandTime);


                            database.getReference().child("chats/" + userid).child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    database.getReference().child("chats/" + userid).child(senderRoom).child("uid").setValue(receiverId);
                                    database.getReference().child("chats/" + userid).child(senderRoom).child("userName").setValue(name);
                                    database.getReference().child("chats/" + receiverId).child(recieverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            binding.EditTextChat.setText("");
                                            binding.EditTextChat.setCursorVisible(true);
                                        }
                                    });

                                }
                            });

                            scrollBottom();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


    }

    void scrollBottom() {

        binding.scrollView.post(new Runnable() {
            @Override
            public void run() {
                binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}