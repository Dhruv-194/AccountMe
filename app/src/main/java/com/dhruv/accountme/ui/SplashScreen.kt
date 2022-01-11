package com.dhruv.accountme.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dhruv.accountme.R
import pl.droidsonroids.gif.GifImageView

class SplashScreen : AppCompatActivity() {
    private lateinit var gifImage: GifImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val actionbar = supportActionBar
        actionbar!!.hide()
        gifImage = findViewById(R.id.gifImageView)
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}