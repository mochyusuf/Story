package com.mocyusuf.story;

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowInsets
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import android.annotation.SuppressLint
import com.mocyusuf.story.Activity.Home
import com.mocyusuf.story.Activity.Welcome
import com.mocyusuf.story.Utils.PrefsManager

@SuppressLint("CustomSplashScreen")

public class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        val prefsManager = PrefsManager(this)
        lifecycleScope.launch {
            delay(2000)
            val intent = if(prefsManager.splash) {
                Intent(this@MainActivity, Home::class.java)
            } else {
                Intent(this@MainActivity, Welcome::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}