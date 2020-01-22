package com.moose.githublite.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.github.paolorotolo.appintro.AppIntro2
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import com.moose.githublite.R
import com.moose.githublite.model.SampleSlide
import kotlinx.android.synthetic.main.slide_five.*
import org.jetbrains.anko.design.snackbar


class AppIntro : AppIntro2(){
    private lateinit var sharedPref: SharedPreferences
    var provider = OAuthProvider.newBuilder("github.com")
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var token:String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = this.getSharedPreferences("com.moose.githublite.shared", Context.MODE_PRIVATE)!!
        addSlide(SampleSlide.newInstance(R.layout.slide_two))
        addSlide(SampleSlide.newInstance(R.layout.slide_three))
        addSlide(SampleSlide.newInstance(R.layout.slide_four))
        addSlide(SampleSlide.newInstance(R.layout.slide_five))
        showSkipButton(false)
        isProgressButtonEnabled = false

        val scopes: ArrayList<String?> = object : ArrayList<String?>() {
            init {
                add("user")
                add("repo")
            }
        }
        provider.setScopes(scopes)

    }

    fun btnClick(view: View) {
        spinner.visibility = View.VISIBLE
        btn.isEnabled = false
        firebaseAuth.startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener{ authResult: AuthResult ->
               token =  (authResult.credential as OAuthCredential?)!!.accessToken
                addToShared()
            }
            .addOnFailureListener { exception ->
                relative.snackbar("${exception.message}")
            }
    }

    private fun addToShared() {
        sharedPref.edit()
            .putString("token", token)
            .apply().run {
                val intent = Intent(this@AppIntro, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
}

