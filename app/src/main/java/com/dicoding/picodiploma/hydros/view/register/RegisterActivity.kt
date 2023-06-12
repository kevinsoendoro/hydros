package com.dicoding.picodiploma.hydros.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.hydros.CustomEditText
import com.dicoding.picodiploma.hydros.R
import com.dicoding.picodiploma.hydros.databinding.ActivityRegisterBinding
import com.dicoding.picodiploma.hydros.view.login.LoginActivity
import com.dicoding.picodiploma.hydros.view.welcome.Onboarding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()

        binding.textView.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAction() {
        binding.emailEditText.apply {
            isEmail = true
            setOnClearListener(object : CustomEditText.OnClearListener {
                override fun onClear() {
                    binding.edRegisterEmail.error = null
                }
            })
        }

        binding.passwordEditText.apply {
            isPassword = true
            setOnClearListener(object : CustomEditText.OnClearListener {
                override fun onClear() {
                    binding.edRegisterPassword.error = null
                }
            })
        }

        binding.buttonRegister.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val isNameEmpty = name.isEmpty()
            val email = binding.emailEditText.text.toString()
            val isEmailEmpty = email.isEmpty()
            val password = binding.passwordEditText.text.toString()
            val isPasswordEmpty = password.isEmpty()

            binding.nameEditText.error = if (isNameEmpty) getString(R.string.warn_field) else null
            binding.emailEditText.error = if (isEmailEmpty) getString(R.string.warn_field) else null
            binding.passwordEditText.error = if (isPasswordEmpty) getString(R.string.warn_field) else null

            if (!isNameEmpty && !isEmailEmpty && !isPasswordEmpty) {
                showLoading(true)
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    showLoading(false)
                    if (it.isSuccessful) {
                        AlertDialog.Builder(this).apply {
                            setTitle(resources.getString(R.string.msg_title))
                            setMessage(resources.getString(R.string.msg_text_register))
                            setPositiveButton(resources.getString(R.string.msg_button)) { _, _ ->
                                val intent = Intent(context, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                            firebaseAuth.signOut()
                        }
                    } else {
                        Toast.makeText(this, R.string.warn_email_exists, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this, Onboarding::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
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
            duration = 3000
            startDelay = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val animators = listOf(
            binding.imageView,
            binding.edRegisterName,
            binding.edRegisterEmail,
            binding.edRegisterPassword,
            binding.buttonRegister,
            binding.textView
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