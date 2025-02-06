package com.android1.weatherappassement.Model

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.android1.weatherappassement.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            var i= Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        },3000)
    }
}