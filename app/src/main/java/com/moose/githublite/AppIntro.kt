package com.moose.githublite

import android.os.Bundle
import android.widget.Toast
import com.github.paolorotolo.appintro.AppIntro2


class AppIntro : AppIntro2(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //slides
        addSlide(SampleSlide.newInstance(R.layout.slide_one))
        addSlide(SampleSlide.newInstance(R.layout.slide_two))
        addSlide(SampleSlide.newInstance(R.layout.slide_three))
        addSlide(SampleSlide.newInstance(R.layout.slide_four))
        showSkipButton(false)
        setProgressButtonEnabled(false)
    }

    fun btn_click() {
        Toast.makeText(this, "Button click successful", Toast.LENGTH_SHORT).show()
    }
}
