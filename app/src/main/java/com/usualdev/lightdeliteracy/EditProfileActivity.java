package com.usualdev.lightdeliteracy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class    EditProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail, editUsername, editPassword, editDay, editAdmno;
    private Button saveButton;
    private String nameUser, emailUser, usernameUser, passwordUser, dayUser, admUser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize EditText fields and Button
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editDay = findViewById(R.id.editDay);
        editAdmno = findViewById(R.id.editAdmno);
        saveButton = findViewById(R.id.saveButton);

        // Load user data into fields
        showData();

        // Set up the Save button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the password input
                String newPassword = editPassword.getText().toString().trim();

                // Check if the password field is not empty
                if (newPassword.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return; // Do not proceed with saving
                }

                // Proceed to save changes if password is provided
                boolean changesMade = false;

                if (isNameChanged()) changesMade = true;
                if (isEmailChanged()) changesMade = true;
                if (isPasswordChanged()) changesMade = true;
                if (isDayChanged()) changesMade = true;
                if (isAdmnoChanged()) changesMade = true;

                // Display appropriate message
                if (changesMade) {
                    Toast.makeText(EditProfileActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileActivity.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Check if the Name field has changed and update Firebase
    private boolean isNameChanged() {
        String newName = editName.getText().toString().trim();
        if (!newName.equals(nameUser)) {
            reference.child(usernameUser).child("name").setValue(newName);
            nameUser = newName;
            return true;
        }
        return false;
    }

    // Check if the Email field has changed and update Firebase
    private boolean isEmailChanged() {
        String newEmail = editEmail.getText().toString().trim();
        if (!newEmail.equals(emailUser)) {
            reference.child(usernameUser).child("email").setValue(newEmail);
            emailUser = newEmail;
            return true;
        }
        return false;
    }

    // Check if the Password field has changed and update Firebase
    private boolean isPasswordChanged() {
        String newPassword = editPassword.getText().toString().trim();
        if (!newPassword.equals(passwordUser)) {
            reference.child(usernameUser).child("password").setValue(newPassword);
            passwordUser = newPassword;
            return true;
        }
        return false;
    }

    // Check if the Day field has changed and update Firebase
    private boolean isDayChanged() {
        String newDay = editDay.getText().toString().trim();
        if (!newDay.equals(dayUser)) {
            reference.child(usernameUser).child("day").setValue(newDay);
            dayUser = newDay;
            return true;
        }
        return false;
    }

    // Check if the Admission Number field has changed and update Firebase
    private boolean isAdmnoChanged() {
        String newAdmno = editAdmno.getText().toString().trim();
        if (!newAdmno.equals(admUser)) {
            reference.child(usernameUser).child("admno").setValue(newAdmno);
            admUser = newAdmno;
            return true;
        }
        return false;
    }

    // Populate the fields with existing data from Intent
    private void showData() {
        Intent intent = getIntent();

        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");
        passwordUser = intent.getStringExtra("password");
        dayUser = intent.getStringExtra("day");
        admUser = intent.getStringExtra("admno");

        // Set default values if any of the data is null
        if (nameUser == null) nameUser = "";
        if (emailUser == null) emailUser = "";
        if (usernameUser == null) usernameUser = "";
        if (passwordUser == null) passwordUser = "";
        if (dayUser == null) dayUser = "";
        if (admUser == null) admUser = "";

        // Populate fields with existing data
        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
        editPassword.setText(passwordUser);
        editDay.setText(dayUser);
        editAdmno.setText(admUser);
    }
}