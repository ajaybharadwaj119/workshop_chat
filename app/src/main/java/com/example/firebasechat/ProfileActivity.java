package com.example.firebasechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebasechat.databinding.ActivityProfileBinding;
import com.example.firebasechat.databinding.DialogLogoutBinding;
import com.example.firebasechat.utils.MyPrefs;
import com.example.firebasechat.utils.OnItemViewClickListener;
import com.example.firebasechat.utils.UserData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    List<Users> chatMessages = new ArrayList<Users>();
    AdapterChat adapterAllChat;
    String userName = "", userType = "", message = "", uid = "";
    Activity activity;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String currentUserId = "";
    DialogLogoutBinding dialogLogoutBinding;
    AlertDialog logoutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        database = FirebaseDatabase.getInstance("https://chat-firebase-cd16a-default-rtdb.firebaseio.com/");
        auth = FirebaseAuth.getInstance();

        MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).putBoolean("login", true);

        final List<Users> messageModels = new ArrayList<>();

       /* if (messageModels.size()==0){

            binding.noResults.setVisibility(View.VISIBLE);
            binding.recyclerchat.setVisibility(View.GONE);

        }else {
            binding.noResults.setVisibility(View.GONE);
            binding.recyclerchat.setVisibility(View.VISIBLE);

        }*/


        adapterAllChat = new AdapterChat(this, messageModels, new OnItemViewClickListener() {
            @Override
            public void onClick(View v, int i) {

                if (v.getId() == R.id.chatuserlist) {

                    Users lists = adapterAllChat.list.get(i);
                    Intent intent = new Intent(activity, ChatDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userID", lists.getUserId());
                    Log.i("sendsssData", lists.getUserId());
                    intent.putExtras(bundle);
                    startActivity(intent);


                }
            }
        });

        binding.recyclerchat.setAdapter(adapterAllChat);
        LinearLayoutManager layoutManger = new LinearLayoutManager(this);
        layoutManger.setStackFromEnd(true);
        binding.recyclerchat.setLayoutManager(layoutManger);



        dialogLogoutBinding = DialogLogoutBinding.inflate(getLayoutInflater());
        logoutDialog = getCustomAlertDialog(activity, dialogLogoutBinding.getRoot());


        dialogLogoutBinding.tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.dismiss();
            }
        });


        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog.show();
            }
        });

        dialogLogoutBinding.tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MyPrefs.getInstance(activity).putBoolean(UserData.KEY_LOGGED_IN, false);
                FirebaseAuth.getInstance().signOut();

                // Delete the user's data from the Realtime Database
                String currentUserId = MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).getString(UserData.KEY_USER_ID);

                if (currentUserId != null) {
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);

                    // Remove the user's data
                    userRef.removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                // User data removed successfully
                                Toast.makeText(ProfileActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();

                                // Perform the logout action (e.g., navigate to the login screen)
                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Close the current activity
                            } else {
                                // Handle the error here
                                Toast.makeText(ProfileActivity.this, "Error logging out", Toast.LENGTH_SHORT).show();
                                Log.e("Firebase", "Error removing user data", databaseError.toException());
                            }
                        }
                    });

                }
                MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).putBoolean("login", false);
                Intent intent = new Intent(activity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



/*
        database.getReference().child("Users/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                List<Users> filteredUsers = new ArrayList<>();

                try {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Users model = snapshot1.getValue(Users.class);


                        if (model != null && !model.getUserId().equals(currentUser)) {
                            filteredUsers.add(model);
                            Log.i("checkFirebaseMessage", model.getUserId());
                            String userId = model.getUserId();
                            MyPrefs.getInstance(activity,CommonConstants.SHARED_PREF).putString(UserData.KEY_LIST_ID,userId);
                            model.setUserId(snapshot1.getKey());
                            messageModels.add(model);
                        }

                    }

                } catch (Exception e) {

                }

                adapterAllChat.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/


        database.getReference().child("Users/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                List<Users> filteredUsers = new ArrayList<>();

                try {
                    String currentUser = MyPrefs.getInstance(activity, CommonConstants.SHARED_PREF).getString(UserData.KEY_USER_ID);
                    Log.i("checkFirebaseMessage1", currentUser);

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        Users model = snapshot1.getValue(Users.class);

                        if (model != null && !model.getUserId().equals(currentUser)) {
                            filteredUsers.add(model);
                            Log.i("checkFirebaseMessage", model.getUserId());
                            String userId = model.getUserId();
                            model.setUserId(snapshot1.getKey());
                            messageModels.add(model);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapterAllChat.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });


    }

    public static AlertDialog getCustomAlertDialog(Activity activity, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}