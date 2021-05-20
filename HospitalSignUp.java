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
import com.google.firebase.database.FirebaseDatabase;

public class HospitalSignUp extends AppCompatActivity implements View.OnClickListener {

    private TextView banner,register,logherebtn2;
    private Button hossignback;
    private EditText editHosName, edithosAdd, editTextEmail, editTextPassword, editHosPhone;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitalsignup);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView)findViewById(R.id.banner);
        banner.setOnClickListener(this);

        logherebtn2 = (TextView)findViewById(R.id.logherebtn2);
        logherebtn2.setOnClickListener(this);

        register = (Button)findViewById(R.id.registerbtn);
        register.setOnClickListener(this);

        hossignback = (Button)findViewById(R.id.hossignback);
        hossignback.setOnClickListener(this);

        editHosName = (EditText) findViewById(R.id.hosName);
        edithosAdd = (EditText)findViewById(R.id.hosAddress);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editHosPhone = (EditText) findViewById(R.id.hosPhone);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.logherebtn2:
                startActivity(new Intent(this, HospitalLogin.class));
                break;

            case R.id.hossignback:
                startActivity(new Intent(this, HospitalWelcome.class));
                break;

            case R.id.registerbtn:
                registerUser();
                break;
        }

    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String hosName = editHosName.getText().toString().trim();
        String hosAddress = edithosAdd.getText().toString().trim();
        String hosPhone = editHosPhone.getText().toString().trim();

        if (hosName.isEmpty()){
            editHosName.setError("Hospital name is required!");
            editHosName.requestFocus();
            return;
        }

        if (hosAddress.isEmpty()){
            edithosAdd.setError("Hospital Address is required!");
            edithosAdd.requestFocus();
            return;
        }

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

        if (hosPhone.isEmpty()){
            editTextPassword.setError("Phone Number is required!");
            editTextPassword.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email,password)


                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(hosName,hosAddress,email);

                          // add to firestore

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(HospitalSignUp.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }


                                    else {
                                        Toast.makeText(HospitalSignUp.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });

                        }

                        else{
                            Toast.makeText(HospitalSignUp.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });


    }
}