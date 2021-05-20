package com.example.mpproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recepientwelcome.*

class RecepientWelcome : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recepientwelcome)


        ReceLogin.setOnClickListener {
            var intent = Intent(this, RecepientLogin::class.java)
            startActivity(intent)
        }


        ReceSignUp.setOnClickListener{
        var intent = Intent(this,RecepientSignUp::class.java)
        startActivity(intent)
        }

        Regoingback.setOnClickListener{
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

}