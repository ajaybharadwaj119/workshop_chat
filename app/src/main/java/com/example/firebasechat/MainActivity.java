package com.example.firebasechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.firebasechat.databinding.ActivityMainBinding;
import com.example.firebasechat.utils.MyPrefs;
import com.example.firebasechat.utils.UserData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    int userId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("924497866393-77laee4c2brs0b0kape7vr0f2duldguk.apps.googleusercontent.com")
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.btsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    int RC_SIGN_IN = 40;

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                try {

                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    firebaseAuth(account.getIdToken());

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

        }
    }
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Handle successful sign-in
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    // Proceed to the next activity
                    firebaseAuth(account.getIdToken());
                    Log.d("MainActivity", "onActivityResult - requestCode: " + requestCode + ", resultCode: " + resultCode);

                } catch (ApiException e) {
                    
                    // Handle any other sign-in issues
                    Log.e("GoogleSignInError", "Sign-in failed with code: " + e.getStatusCode());
                    // Display an error message and allow the user to retry
                }
            }
        }
    }

    private void navigateToProfileActivity(GoogleSignInAccount account) {
        // You can pass relevant user information to the ProfileActivity if needed
        String userId = account.getId();
        String userName = account.getDisplayName();

        // Start the ProfileActivity
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userName", userName);
        startActivity(intent);
        finish(); // Optionally, finish the current activity to prevent going back
    }


    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();
                            Users users = new Users();

                            DatabaseReference userCountRef = firebaseDatabase.getReference("UserId/uId");
                            userCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        long lastUserId = (long) dataSnapshot.getValue();

                                        // Increment the last assigned user ID
                                        long newUserId = lastUserId + 1;

                                        // Save the incremented ID as the user's ID
                                        users.setUserId(String.valueOf(newUserId));
                                        dataSnapshot.getRef().setValue(newUserId);
                                        String id = users.getUserId();
                                        MyPrefs.getInstance(MainActivity.this, CommonConstants.SHARED_PREF).putString(UserData.KEY_USER_ID, String.valueOf(newUserId));
                                        users.setName(user.getDisplayName());
                                        users.setProfile(user.getPhotoUrl().toString());
                                        firebaseDatabase.getReference().child("Users").child(String.valueOf(newUserId)).setValue(users);

                                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                                        startActivity(intent);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle errors here

                                }
                            });


                        } else {
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}