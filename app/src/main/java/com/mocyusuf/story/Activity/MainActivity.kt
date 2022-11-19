package com.mocyusuf.story.Activity;

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mocyusuf.story.R
import com.mocyusuf.story.Utils.PrefsManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupView()
        val prefsManager = PrefsManager(this)
        lifecycleScope.launch {
            delay(2000)
            val intent = when {
                prefsManager.exampleBoolean -> {
                    Intent(this@MainActivity, Home::class.java)
                }
                prefsManager.isExampleLogin -> {
                    Intent(this@MainActivity, Login::class.java)
                }
                else -> {
                    Intent(this@MainActivity, Welcome::class.java)
                }
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