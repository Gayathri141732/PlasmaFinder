package com.example.mpproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    logout = (Button) findViewById(R.id.logout);

    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Profile.this,MainActivity.class));
        }
    });


    user = FirebaseAuth.getInstance().getCurrentUser();
    reference = FirebaseDatabase.getInstance().getReference("User");
    userID = user.getUid();

    final TextView greetTextView = (TextView)findViewById(R.id.greet);
    final TextView fullNameTextView = (TextView)findViewById(R.id.userFullName);
    final TextView emailTextView = (TextView)findViewById(R.id.userEmailAddress);
    final TextView ageTextView = (TextView)findViewById(R.id.userAge);


    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            User userProfile = snapshot.getValue(User.class);

            if (userProfile != null){
                String fullName = userProfile.hosName;
                String email = userProfile.email;
                String age = userProfile.hosAddress;

                greetTextView.setText("Hi "+fullName+" !");
                fullNameTextView.setText(fullName);
                emailTextView.setText(email);
                ageTextView.setText(age);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

            Toast.makeText(Profile.this,"Something went wrong",Toast.LENGTH_LONG).show();

        }
    });


    }
}