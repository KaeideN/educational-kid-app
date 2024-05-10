package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {

    private TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    private TextInputEditText emailEditText, passwordEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        emailTextInputLayout = findViewById(R.id.registerEmailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.registerPasswordTextInputLayout);
        emailEditText = findViewById(R.id.registerEmailEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Perform validation checks
                if (TextUtils.isEmpty(email)) {
                    emailTextInputLayout.setError("Enter your email");
                    return;
                } else {
                    emailTextInputLayout.setError(null);
                }

                if (password.length()<6) {
                    passwordTextInputLayout.setError("Password should be at least 6 characters");
                    return;
                } else {
                    passwordTextInputLayout.setError(null);
                }

                performRegistration(email, password);


            }
        });

    }

    private void performRegistration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);


                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                // If the registration failed due to the email already being in use
                                Toast.makeText(Register.this, "This email is already registered.", Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
//
    }
}
