package com.usualdev.lightdeliteracy;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admin extends AppCompatActivity {

    private TextView welcomeMessage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();
        welcomeMessage = findViewById(R.id.welcome_message);

        // Get the current user and display a welcome message
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            welcomeMessage.setText("Welcome, Admin! Email: " + email);
        } else {
            welcomeMessage.setText("Welcome, Admin!");
        }
    }

    // Additional methods for admin functionalities can be added here
}