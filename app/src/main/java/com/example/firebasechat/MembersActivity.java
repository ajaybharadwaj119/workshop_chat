package com.example.firebasechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebasechat.databinding.ActivityMembersBinding;
import com.example.firebasechat.databinding.ActivityProfileBinding;
import com.example.firebasechat.utils.MembersAdapter;
import com.example.firebasechat.utils.OnItemViewClickListener;
import com.example.firebasechat.utils.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MembersActivity extends AppCompatActivity {

    ActivityMembersBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private MembersAdapter adapter;
    Activity activity;

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMembersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

      //  mDatabase = FirebaseDatabase.getInstance().getReference().child("users");







        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerchat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        /*adapter = new MembersAdapter(userList);
        recyclerView.setAdapter(adapter);*/

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logout
                signOut();
            }
        });

       // loadMembersList();
    }


    private void displayUserList() {
        adapter = new MembersAdapter(userList);
        recyclerView.setAdapter(adapter);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        // Redirect to the login screen or perform any other desired action
        startActivity(new Intent(MembersActivity.this, MainActivity.class));
        finish();
    }

}