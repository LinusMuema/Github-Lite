package com.moose.githublite.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.moose.githublite.R

class Splash : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var firebaseuser: FirebaseUser? = firebaseAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        if(firebaseuser != null)
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
