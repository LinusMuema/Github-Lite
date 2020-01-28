package com.moose.githublite.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.paolorotolo.appintro.AppIntro2
import com.moose.githublite.model.SampleSlide
import com.moose.githublite.R
import kotlinx.android.synthetic.main.slide_five.*
import net.openid.appauth.*
import org.jetbrains.anko.design.snackbar


class AppIntro : AppIntro2(){
    private lateinit var configuration: AuthorizationServiceConfiguration
    private lateinit var clientAuthentication: ClientAuthentication
    private lateinit var shared: SharedPreferences
    private lateinit var clientId: String
    private lateinit var clientSecret: String
    private lateinit var redirectUri: Uri
    private lateinit var callbackCode: String
    private var authResponse: AuthorizationResponse? = null
    private val authRequestCode = 0
    private lateinit var sharedPref: SharedPreferences
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

        clientId = resources.getString(R.string.client_id)
        clientSecret = resources.getString(R.string.client_secret)
        redirectUri = Uri.parse(resources.getString(R.string.redirect_uri))

        configuration = AuthorizationServiceConfiguration(
            Uri.parse("https://github.com/login/oauth/authorize"),
            Uri.parse("https://github.com/login/oauth/access_token")
        )
        clientAuthentication = ClientSecretBasic(clientSecret)

    }

    fun btnClick(view: View) {
        spinner.visibility = View.VISIBLE
        btn.isEnabled = false
        val authRequest =
            AuthorizationRequest.Builder(configuration, clientId, ResponseTypeValues.CODE, redirectUri)
                .setScope("user, repo")
                .build()

        val service = AuthorizationService(this)
        val intent = service.getAuthorizationRequestIntent(authRequest)
        startActivityForResult(intent, authRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == authRequestCode){
            authResponse = AuthorizationResponse.fromIntent(data!!)
            if (authResponse != null) {
                getToken()
            }
            else{
                btn.isEnabled = true
                relative.snackbar("Something went wrong! Try again later.")
            }
        }
    }

    private fun getToken() {
        val request =  authResponse!!.createTokenExchangeRequest()
        val service = AuthorizationService(this)
        service.performTokenRequest(request, clientAuthentication)
        { tokenResponse, tokenException ->
            if (tokenResponse != null){
                token = tokenResponse.accessToken!!
                addToShared()
            }
            else{
                relative.snackbar(" ${tokenException!!.message}")
            }
        }
    }

    private fun addToShared() {
        sharedPref.edit()
            .putString("token", token)
            .putBoolean("loggedIn", true)
            .apply().run {
                val intent = Intent(this@AppIntro, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
}

