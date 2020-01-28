package com.moose.githublite.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.moose.githublite.R

class Splash : AppCompatActivity() {
    private lateinit var shared: SharedPreferences
    private var loggedIn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        shared = this.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        loggedIn = shared.getBoolean("loggedIn", false)
    }

    override fun onStart() {
        super.onStart()
        if(loggedIn)
            skipIntro()
        else
            moveToIntro()
    }

    private fun moveToIntro() {
        Handler().postDelayed({
            val intent = Intent(this, AppIntro::class.java)
            startActivity(intent)
            finish()
        },3000)
    }

    private fun skipIntro() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}
