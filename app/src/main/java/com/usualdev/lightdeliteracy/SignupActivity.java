package com.usualdev.lightdeliteracy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private EditText signupName, signupUsername, signupEmail, signupPassword, signupday, signupphone, signupadmissionno;
    private TextView loginRedirectText;
    private Button signupButton;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupday = findViewById(R.id.signup_day);
        signupadmissionno = findViewById(R.id.signup_admissionno);
        signupphone = findViewById(R.id.signup_phone);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");

                    String name = signupName.getText().toString();
                    String email = signupEmail.getText().toString();
                    String username = signupUsername.getText().toString();
                    String password = signupPassword.getText().toString();
                    String day = signupday.getText().toString();
                    String phone = signupphone.getText().toString();
                    String admno = signupadmissionno.getText().toString();

                    // Creating a helper class object and storing data in the database
                    Helper helperClass = new Helper(name, email, username, password, day, phone, admno, "user");
                    reference.child(username).setValue(helperClass);

                    Toast.makeText(SignupActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Validate input fields before signing up
    private boolean validateInputs() {
        if (signupName.getText().toString().isEmpty()) {
            signupName.setError("Name cannot be empty");
            signupName.requestFocus();
            return false;
        }
        if (signupEmail.getText().toString().isEmpty()) {
            signupEmail.setError("Email cannot be empty");
            signupEmail.requestFocus();
            return false;
        }
        if (signupUsername.getText().toString().isEmpty()) {
            signupUsername.setError("Username cannot be empty");
            signupUsername.requestFocus();
            return false;
        }
        if (signupPassword.getText().toString().isEmpty()) {
            signupPassword.setError("Password cannot be empty");
            signupPassword.requestFocus();
            return false;
        }
        if (signupday.getText().toString().isEmpty()) {
            signupday.setError("Day cannot be empty");
            signupday.requestFocus();
            return false;
        }
        if (signupphone.getText().toString().isEmpty()) {
            signupphone.setError("Phone number cannot be empty");
            signupphone.requestFocus();
            return false;
        }
        if (signupadmissionno.getText().toString().isEmpty()) {
            signupadmissionno.setError("Admission number cannot be empty");
            signupadmissionno.requestFocus();
            return false;
        }
        return true;
    }
}
