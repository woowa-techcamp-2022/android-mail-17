package com.example.android_mail_17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.android_mail_17.databinding.ActivityLoginBinding
import com.example.android_mail_17.others.Utils
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private var _binding : ActivityLoginBinding? = null
    private val binding: ActivityLoginBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextChangeListener()
    }

    private fun setTextChangeListener() {
        binding.loginNickNameEditText.addTextChangedListener {
            when {
                it.isNullOrBlank() -> resetError(binding.loginNickNameLayout)
                Utils.checkNicknameConstraint(it.toString()) -> resetError(binding.loginNickNameLayout)
                else -> setNicknameError(binding.loginNickNameLayout)
            }
        }
        binding.loginEmailEditText.addTextChangedListener {
            when {
                it.isNullOrBlank() -> resetError(binding.loginEmailLayout)
                Utils.checkEmailConstraint(it.toString()) -> resetError(binding.loginEmailLayout)
                else -> setEmailError(binding.loginEmailLayout)
            }
        }
    }

    private fun resetError(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
    }

    private fun setNicknameError(textInputLayout: TextInputLayout) {
        textInputLayout.error = getString(R.string.nickname_error)
    }

    private fun setEmailError(textInputLayout: TextInputLayout) {
        textInputLayout.error = getString(R.string.email_error)
    }
}