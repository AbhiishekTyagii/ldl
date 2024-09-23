package com.usualdev.lightdeliteracy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText loginUsername, loginPassword;
    private Button loginButton;
    private TextView signupRedirectText;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseDatabase.getInstance().getReference();

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUsername() && validatePassword()) {
                    authenticateUserWithUsernameAndPassword();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateUsername() {
        String val = loginUsername.getText().toString().trim();
        if (val.isEmpty()) {
            loginUsername.setError("Username cannot be empty");
            return false;
        }
        loginUsername.setError(null);
        return true;
    }

    private boolean validatePassword() {
        String val = loginPassword.getText().toString().trim();
        if (val.isEmpty()) {
            loginPassword.setError("Password cannot be empty");
            return false;
        }
        loginPassword.setError(null);
        return true;
    }

    private void authenticateUserWithUsernameAndPassword() {
        String inputUsername = loginUsername.getText().toString().trim();
        String inputPassword = loginPassword.getText().toString().trim();

        // Query the database for the user
        db.child("users").orderByChild("username").equalTo(inputUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String storedPassword = snapshot.child("password").getValue(String.class);

                        // Log values for debugging
                        Log.d(TAG, "Input Password: " + inputPassword);
                        Log.d(TAG, "Stored Password: " + storedPassword);

                        if (storedPassword != null && storedPassword.equals(inputPassword)) {
                            String username = snapshot.child("username").getValue(String.class);
                            saveUserData(username);
                        } else {
                            showError("Incorrect password");
                        }
                    }
                } else {
                    showError("Username does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error fetching user data: ", databaseError.toException());
                Toast.makeText(LoginActivity.this, "Error fetching user data", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        loginPassword.setError(message);
        loginPassword.requestFocus();
    }

    private void saveUserData(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);

        // Fetch the user role
        db.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    editor.putString("role", role != null ? role : "user");
                    editor.apply();
                    redirectToAppropriateActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Error fetching role: " + error.getMessage());
            }
        });
    }

    private void redirectToAppropriateActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "user");

        Intent intent;
        switch (role) {
            case "admin":
                intent = new Intent(LoginActivity.this, Admin.class);
                break;
            case "daycoordinator":
                intent = new Intent(LoginActivity.this, DayCoordinators.class);
                break;
            default:
                intent = new Intent(LoginActivity.this, MainActivity.class);
                break;
        }

        startActivity(intent);
        finish();
    }
}
