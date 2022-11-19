package com.mocyusuf.story.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mocyusuf.story.R
import com.mocyusuf.story.Utils.PrefsManager

class Welcome : AppCompatActivity() {
    private lateinit var prefsManager: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        prefsManager = PrefsManager(this)
        supportActionBar?.hide()
        val btnStart = findViewById<Button>(R.id.btn_start)
        btnStart.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            prefsManager.isExampleLogin = true
            finish()
        }
    }
}