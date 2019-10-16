package com.example.android_recruitment.controller

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.example.android_recruitment.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    private val activity: AppCompatActivity = this
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val uri =
            Uri.parse("android.resource://" + activity.packageName + "/" + R.raw.logo_splash)
        Glide.with(context).asGif().load(uri).into(splash_image)

        val handle = Handler()
        handle.postDelayed({
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }, 3000)
    }
}
