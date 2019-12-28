package com.moose.githublite
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreference =  getSharedPreferences("GITHUB", Context.MODE_PRIVATE)
        val mail = sharedPreference.getString("user_mail", null)
        Toast.makeText(this, mail, Toast.LENGTH_SHORT).show()
    }
}
