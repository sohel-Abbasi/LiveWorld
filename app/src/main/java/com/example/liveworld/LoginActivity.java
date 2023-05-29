package com.example.liveworld;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText emailBox,passwordBox;
   Button loginbtn,signupbtn;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      auth=FirebaseAuth.getInstance();
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.createbtn);

        loginbtn.setOnClickListener(view -> {
            String email,password;
            email=emailBox.getText().toString();
            password=passwordBox.getText().toString();
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                  startActivity(new Intent(LoginActivity.this,DashboardActivity.class) );
                }
                else{
                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        });

       signupbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               LoginActivity.this.startActivity(new Intent(LoginActivity.this, SignupActivity.class));
           }
       });
    }
}