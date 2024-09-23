package com.usualdev.lightdeliteracy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private CardView viewAttendanceCard;
    private CardView profileCard;
    private CardView logoutCard;
    private CardView refreshCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        welcomeMessage = findViewById(R.id.welcomeMessage);
        viewAttendanceCard = findViewById(R.id.viewAttendanceCard);
        profileCard = findViewById(R.id.profileCard);
        logoutCard = findViewById(R.id.logoutCard);
        refreshCard = findViewById(R.id.refreshCard);

        // Fetch username from Intent or Database
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null) {
            welcomeMessage.setText("Welcome, " + username);
        }

        // Set click listeners for buttons
        viewAttendanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle view attendance click
               // Intent intent = new Intent(MembersActivity.this, ViewAttendanceActivity.class);
                //startActivity(intent);
            }
        });

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle profile click
               // Intent intent = new Intent(MembersActivity.this, ProfileActivity.class);
              //  startActivity(intent);
            }
        });

        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout click
                // For example, clear user session and navigate to login screen
               // Intent intent = new Intent(MembersActivity.this, LoginActivity.class);
               // startActivity(intent);
                finish(); // Close current activity
            }
        });

        refreshCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle refresh click
                // For example, refresh user data
                refreshData();
            }
        });
    }

    private void refreshData() {
        // Implement data refresh logic here
        // For example, re-fetch data from the database or API
    }
}
