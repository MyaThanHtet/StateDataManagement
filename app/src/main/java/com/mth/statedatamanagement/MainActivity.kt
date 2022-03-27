package com.mth.statedatamanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnCreateAccount:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCreateAccount=findViewById(R.id.btn_create_account)
        btnCreateAccount.setOnClickListener {

            startActivity(Intent(this,FormScreen::class.java))

        }
    }
}