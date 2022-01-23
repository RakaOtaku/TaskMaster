package com.example.taskmasterr

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var splash: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.top_anim)


        splash = findViewById(R.id.splash)

        splash.startAnimation(fadeAnimation)

        val splashTo = 3000
        val homeIntent = Intent(this,Home::class.java)

        Handler().postDelayed({
            startActivity(homeIntent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finish()
        }, splashTo.toLong())
    }
}