package com.moose.githublite

import android.os.Bundle
import android.view.View
import com.github.paolorotolo.appintro.AppIntro2
import kotlinx.android.synthetic.main.slide_four.*


class AppIntro : AppIntro2(){

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
        var username = name.text.toString()
        if(username.equals(""))
            name_layout.error = "Username cannot be empty"
        else
            githubAuth(username)
    }

    private fun githubAuth(username: String) {

    }
}

