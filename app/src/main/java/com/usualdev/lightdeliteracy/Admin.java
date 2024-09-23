package com.usualdev.lightdeliteracy;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

public class Admin extends AppCompatActivity {

    private TextView welcomeMessage;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        welcomeMessage = findViewById(R.id.welcomeText);

        // Get the current user and display a welcome message
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String username = user.getDisplayName(); // Change this if the username is stored differently
            Log.d("Admin", "Fetched username: " + username);
            fetchAdminName(username);
        } else {
            Log.d("Admin", "User is not logged in.");
            welcomeMessage.setText("Welcome, Admin!");
        }
    }

    private void fetchAdminName(String username) {
        Log.d("Admin", "Fetching name for username: " + username);
        db.child("users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String name = snapshot.child("name").getValue(String.class);
                        Log.d("Admin", "Fetched name: " + name); // Log the name
                        welcomeMessage.setText("Welcome, Admin " + (name != null ? name : "") + "!");
                    }
                } else {
                    Log.d("Admin", "No matching username found in the database.");
                    welcomeMessage.setText("Welcome, Admin!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                welcomeMessage.setText("Error fetching admin data.");
                Log.e("Admin", "Database error: " + databaseError.getMessage());
            }
        });
    }
}
