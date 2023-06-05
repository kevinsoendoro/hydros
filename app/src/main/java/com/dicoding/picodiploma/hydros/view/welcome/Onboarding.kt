package com.dicoding.picodiploma.hydros.view.welcome

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.constraintlayout.motion.widget.MotionLayout
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.databinding.ActivityOnboardingBinding
import com.dicoding.picodiploma.hydros.view.login.LoginActivity
import com.dicoding.picodiploma.hydros.view.register.RegisterActivity

class Onboarding : AppCompatActivity() {

    private val binding: ActivityOnboardingBinding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        buttonClick()
    }

    private fun buttonClick() {
        with (binding) {
            buttonNext.setOnClickListener {
                motionLayout.transitionToNextState()
            }

            buttonSkip.setOnClickListener {
                motionLayout.transitionToState(R.id.end)
            }

            buttonLogin.setOnClickListener {
                val intent = Intent(this@Onboarding, LoginActivity::class.java)
                startActivity(intent)
            }

            buttonRegister.setOnClickListener {
                val intent = Intent(this@Onboarding, RegisterActivity::class.java)
                startActivity(intent)
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