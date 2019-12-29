package com.moose.githublite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.github.paolorotolo.appintro.AppIntro2
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import kotlinx.android.synthetic.main.slide_five.*
import kotlinx.android.synthetic.main.slide_four.*
import org.jetbrains.anko.design.snackbar


class AppIntro : AppIntro2(){

    var provider = OAuthProvider.newBuilder("github.com")
    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var firebaseuser: FirebaseUser? = firebaseAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(SampleSlide.newInstance(R.layout.slide_one))
        addSlide(SampleSlide.newInstance(R.layout.slide_two))
        addSlide(SampleSlide.newInstance(R.layout.slide_three))
        addSlide(SampleSlide.newInstance(R.layout.slide_four))
        addSlide(SampleSlide.newInstance(R.layout.slide_five))
        showSkipButton(false)
        isProgressButtonEnabled = false

        if (firebaseuser!= null)
            updateUI()

    }

    fun btnClick(view: View) {
        spinner.visibility = View.VISIBLE
        firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener{ authResult: AuthResult ->
                updateUI()
            }
            .addOnFailureListener { exception ->
                snackbar(relative, "Something went wrong! Try again later")
            }
    }

    private fun updateUI() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

