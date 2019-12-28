package com.moose.githublite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.paolorotolo.appintro.AppIntro2
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.android.synthetic.main.slide_five.*


class AppIntro : AppIntro2(){

    var provider = OAuthProvider.newBuilder("github.com")
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(SampleSlide.newInstance(R.layout.slide_one))
        addSlide(SampleSlide.newInstance(R.layout.slide_two))
        addSlide(SampleSlide.newInstance(R.layout.slide_three))
        addSlide(SampleSlide.newInstance(R.layout.slide_four))
        addSlide(SampleSlide.newInstance(R.layout.slide_five))
        showSkipButton(false)
        setProgressButtonEnabled(false)

    }

    fun btnClick(view: View) {
        spinner.visibility = View.VISIBLE
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

