package com.dicoding.picodiploma.hydros.view.welcome

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.databinding.ActivityOnboardingBinding
import com.dicoding.picodiploma.hydros.view.login.LoginActivity
import com.dicoding.picodiploma.hydros.view.register.RegisterActivity

class Onboarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        with(binding) {
            buttonNext.setOnClickListener {
                motionLayout.transitionToNextState()
            }

            buttonSkip.setOnClickListener {
                motionLayout.transitionToState(R.id.end)
            }

            buttonLogin.setOnClickListener {
                startActivity(Intent(this@Onboarding, LoginActivity::class.java))
            }

            buttonRegister.setOnClickListener {
                startActivity(Intent(this@Onboarding, RegisterActivity::class.java))
            }
        }
    }

    private fun MotionLayout.transitionToNextState() {
        val currentState = currentState
        val nextState = getNextState(currentState)
        transitionToState(nextState)
    }

    private fun getNextState(currentState: Int): Int {
        return when (currentState) {
            R.id.start -> R.id.page1
            R.id.page1 -> R.id.page2
            R.id.page2 -> R.id.page3
            else -> R.id.end
        }
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController = window.insetsController
            windowInsetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}