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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText emailBox,passwordBox,nameBox;
    Button loginbtn,signupbtn;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database=FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        emailBox = findViewById(R.id.emailBox);
        passwordBox = findViewById(R.id.passwordBox);
        nameBox = findViewById(R.id.nameBox);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.createbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, pass, name;
                email = emailBox.getText().toString();
                pass = passwordBox.getText().toString();
                name = nameBox.getText().toString();

                User user = new User();//object create
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        database.collection("Users")
                                .document().set(user).addOnSuccessListener(unused -> SignupActivity.this.startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
                        Toast.makeText(SignupActivity.this, "Account is Created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignupActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
}