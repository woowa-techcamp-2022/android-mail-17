package com.example.android_mail_17.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.android_mail_17.R
import com.example.android_mail_17.databinding.ActivityLoginBinding
import com.example.android_mail_17.others.Utils
import com.example.android_mail_17.viewmodels.FlagViewModel
import com.example.android_mail_17.viewmodels.InputViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private val flagViewModel by viewModels<FlagViewModel>()
    private val inputViewModel by viewModels<InputViewModel>()
    private var _binding: ActivityLoginBinding? = null
    private val binding: ActivityLoginBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restoreData()
        setTextChangeListener()
        setLiveDataObserver()
        setButtonClickListener()
    }

    private fun restoreData() {
        binding.loginNickNameEditText.setText(inputViewModel.nickname)
        binding.loginEmailEditText.setText(inputViewModel.email)
    }

    private fun setTextChangeListener() {
        binding.loginNickNameEditText.addTextChangedListener {
            it?.let { nickname -> inputViewModel.saveNickname(nickname.toString()) }
            when {
                it.isNullOrBlank() -> {
                    resetError(binding.loginNickNameLayout)
                    flagViewModel.setNicknameFlag(false)
                }
                Utils.checkNicknameConstraint(it.toString()) -> {
                    resetError(binding.loginNickNameLayout)
                    flagViewModel.setNicknameFlag(true)
                }
                else -> {
                    setNicknameError(binding.loginNickNameLayout)
                    flagViewModel.setNicknameFlag(false)
                }
            }
        }

        binding.loginEmailEditText.addTextChangedListener {
            it?.let { email -> inputViewModel.saveEmail(email.toString()) }
            when {
                it.isNullOrBlank() -> {
                    resetError(binding.loginEmailLayout)
                    flagViewModel.setEmailFlag(false)
                }
                Utils.checkEmailConstraint(it.toString()) -> {
                    resetError(binding.loginEmailLayout)
                    flagViewModel.setEmailFlag(true)
                }
                else -> {
                    setEmailError(binding.loginEmailLayout)
                    flagViewModel.setEmailFlag(false)
                }
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

    private fun setLiveDataObserver() {
        flagViewModel.buttonEnabledFlag.observe(this) { flag ->
            binding.loginButton.isEnabled = flag
        }
    }

    private fun setButtonClickListener() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                putExtra("nickname", inputViewModel.nickname)
                putExtra("email", inputViewModel.email)
            })
            finish()
        }
    }
}