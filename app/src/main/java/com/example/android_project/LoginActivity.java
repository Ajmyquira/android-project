package com.example.android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button forgetButton;
    Button loginButton;
    Button signUpButton;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        forgetButton = findViewById(R.id.forgetButton);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, registerActivity.class));
            }
        });

        forgetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Contacte con los desarrolladores", Toast.LENGTH_LONG).show();
            }
        });

        loginButton.setOnClickListener(view -> {
            login_setup();
        });
    }

    public void login_setup(){
        if (!(TextUtils.isEmpty(emailEditText.getText().toString())) && !(TextUtils.isEmpty(passwordEditText.getText().toString()))){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                ShowAlert();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(LoginActivity.this, "Rellene los espacios", Toast.LENGTH_LONG).show();
        }
    }

    public void ShowAlert(){
        Toast.makeText(LoginActivity.this, "Error en la Autenticacion", Toast.LENGTH_LONG).show();
    }

}