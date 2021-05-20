package com.example.mpproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hospitalwelcome.*

class HospitalWelcome : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospitalwelcome)


        hosLogin.setOnClickListener {
            var intent = Intent(this, HospitalLogin::class.java)
            startActivity(intent)
        }


        hosSignUp.setOnClickListener{
            var intent = Intent(this,HospitalSignUp::class.java)
            startActivity(intent)
        }

        hosgoingback.setOnClickListener{
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

}