package com.usualdev.lightdeliteracy;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.core.view.GravityCompat;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Toolbar toolbar;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Initialize Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Find the Toolbar and TextView in the layout
        toolbar = findViewById(R.id.toolbar);
        welcomeText = findViewById(R.id.welcomeText);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setSupportActionBar(toolbar);

        // Setup Navigation Drawer
        setupDrawer();

        // Fetch user information and update welcome message
        fetchUserName();
    }

    private void fetchUserName() {
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        if (name != null && !name.isEmpty()) {
                            welcomeText.setText("Welcome, " + name + "!");
                        } else {
                            welcomeText.setText("Welcome!");
                        }
                    } else {
                        welcomeText.setText("Welcome!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle possible errors
                    welcomeText.setText("Welcome! (Error retrieving data)");
                }
            });
        } else {
            // User is not authenticated, show login prompt message
            welcomeText.setText("Welcome! Please log in.");
        }
    }

    private void setupDrawer() {
        // Handle navigation view item clicks here.
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_profile) {
                // Handle the profile action

                // Handle the settings action
            } else if (id == R.id.nav_logout) {
                // Handle logout
                auth.signOut();
                finish(); // Close activity and go to login screen
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Set up the hamburger icon to open the drawer
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24); // Ensure you have this icon
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }
}