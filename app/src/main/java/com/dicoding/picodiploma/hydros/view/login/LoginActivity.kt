package com.dicoding.picodiploma.hydros.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.dicoding.picodiploma.hydros.MainActivity
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.hydros.databinding.ActivityOnboardingBinding
import com.dicoding.picodiploma.hydros.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        playAnimation()

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.textView.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
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

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.ROTATION_Y, 0f, 360f).apply {
            duration = 5000
            startDelay = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val animators = listOf(
            binding.emailEditText,
            binding.edLoginEmail,
            binding.passwordEditText,
            binding.edLoginPassword,
            binding.buttonLogin
        ).map {
            ObjectAnimator.ofFloat(it, View.ALPHA, 1f).setDuration(500)
        }

        AnimatorSet().apply {
            playSequentially(*animators.toTypedArray())
            startDelay = 500
            start()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

