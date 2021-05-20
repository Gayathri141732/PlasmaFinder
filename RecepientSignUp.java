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

public class RecepientSignUp extends AppCompatActivity implements View.OnClickListener {


    private Button recsignback;

    private TextView banner,register,logherebtn;
    private EditText editTextFullname, editTextAge, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepientsignup);

        logherebtn = (TextView) findViewById(R.id.logherebtn);
        logherebtn.setOnClickListener(this);

        recsignback = (Button) findViewById(R.id.recsignback);
        recsignback.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();

        banner = (TextView)findViewById(R.id.banner);
        banner.setOnClickListener(this);



        register = (Button)findViewById(R.id.registerbtn);
        register.setOnClickListener(this);



        editTextFullname = (EditText) findViewById(R.id.hosName);
        editTextAge = (EditText)findViewById(R.id.hosAddress);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logherebtn:
                startActivity(new Intent(this, RecepientLogin.class));
                break;

            case R.id.recsignback:
                startActivity(new Intent(this, RecepientWelcome.class));
                break;

            case R.id.registerbtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullname.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextFullname.setError("Full name is required!");
            editTextFullname.requestFocus();
            return;
        }

        if (age.isEmpty()){
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
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



        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(fullName,age,email);


                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RecepientSignUp.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }


                                    else {
                                        Toast.makeText(RecepientSignUp.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });

                        }

                        else{
                            Toast.makeText(RecepientSignUp.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });


    }
}