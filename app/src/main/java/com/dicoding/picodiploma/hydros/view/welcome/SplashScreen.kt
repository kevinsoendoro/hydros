package com.dicoding.picodiploma.hydros.view.welcome

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.view.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private companion object {
        const val DELAY_DURATION_MS = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setupView()
        delayAndStartMainActivity()
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

    private fun delayAndStartMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startMainActivity()
        }, DELAY_DURATION_MS)
    }

    private fun startMainActivity() {
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        overridePendingTransition(0, 0)
        finish()
    }
}