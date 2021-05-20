package com.example.mpproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Recipient.setOnClickListener {
            var intent = Intent(this, RecepientWelcome::class.java)
            startActivity(intent)
        }


        hospital.setOnClickListener {
            var intent = Intent(this, HospitalWelcome::class.java)
            startActivity(intent)
        }

    }
}