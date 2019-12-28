package com.moose.githublite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.paolorotolo.appintro.AppIntro2
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import kotlinx.android.synthetic.main.slide_four.*


class AppIntro : AppIntro2(){

    var provider = OAuthProvider.newBuilder("github.com")
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(SampleSlide.newInstance(R.layout.slide_one))
        addSlide(SampleSlide.newInstance(R.layout.slide_two))
        addSlide(SampleSlide.newInstance(R.layout.slide_three))
        addSlide(SampleSlide.newInstance(R.layout.slide_four))
        showSkipButton(false)
        setProgressButtonEnabled(false)

    }

    fun btnClick(view: View) {
        firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener{ result: AuthResult ->
               Log.d("success", result.credential.toString())
                updateUI()
            }
            .addOnFailureListener {
                    exception ->
                Log.d("failure",exception.toString())
            }
    }

    private fun updateUI() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

