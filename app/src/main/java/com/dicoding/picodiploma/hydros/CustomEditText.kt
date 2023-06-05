package com.dicoding.picodiploma.hydros

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText : AppCompatEditText {

    private val passwordRegex = Regex(pattern = "^(?=.*\\d)(?=.*[a-zA-Z]).{8,}\$")

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                error = when {
                    isEmail && !Patterns.EMAIL_ADDRESS.matcher(input).matches() -> context.getString(R.string.warn_email_customize)
                    isPassword && !input.matches(passwordRegex) -> context.getString(R.string.warn_password_customize)
                    else -> null
                }

                if (s.isNullOrEmpty()) {
                    error = null
                }
            }
        })
    }

    var isEmail: Boolean = false
    var isPassword: Boolean = false
}