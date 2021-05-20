package com.example.mpproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HospitalLogin extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn,hoslogback;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitallogin);

        register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(this);


        hoslogback = (Button) findViewById(R.id.hoslogback);
        hoslogback.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, HospitalSignUp.class));
                break;

            case R.id.hoslogback:
                startActivity(new Intent(this, HospitalWelcome.class));
                break;

            case R.id.signIn:
                userLogin();
                break;

        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Provide a valid email address");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("Password should contain atleast 6 characters");
            editTextPassword.requestFocus();
            return;
        }



        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified()) {

                       Toast.makeText(HospitalLogin.this,"Mudinchu pa",Toast.LENGTH_LONG).show();

                    }else {
                        user.sendEmailVerification();
                        Toast.makeText(HospitalLogin.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
                    }


                }else {
                    Toast.makeText(HospitalLogin.this,"Failed to login! Try again",Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}