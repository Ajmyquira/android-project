package com.example.android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {
    Button registerButton;
    EditText emailEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Registrarse");

        registerButton = findViewById(R.id.registerButton);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        registerButton.setOnClickListener(view -> {
            register_setup();
        });
    }

    public void register_setup() {
        if (!(TextUtils.isEmpty(emailEditText.getText().toString())) && !(TextUtils.isEmpty(passwordEditText.getText().toString()))){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(registerActivity.this, LoginActivity.class));
                            } else {
                                ShowAlert();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(registerActivity.this, "Rellene los espacios", Toast.LENGTH_LONG).show();
        }
    }

    public void ShowAlert(){
        Toast.makeText(registerActivity.this, "Error en el registro", Toast.LENGTH_LONG).show();
    }
}