package com.example.android_mail_17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.example.android_mail_17.databinding.ActivityLoginBinding
import com.example.android_mail_17.others.Utils
import com.example.android_mail_17.viewmodels.FlagViewModel
import com.example.android_mail_17.viewmodels.InputViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private val flagViewModel by viewModels<FlagViewModel>()
    private val inputViewModel by viewModels<InputViewModel>()
    private var _binding: ActivityLoginBinding? = null
    val binding: ActivityLoginBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restoreData()
        setTextChangeListener()
        setLiveDataObserver()
    }

    private fun restoreData() {
        binding.loginNickNameEditText.setText(inputViewModel.nickname)
        binding.loginEmailEditText.setText(inputViewModel.email)
    }

    private fun setTextChangeListener() {
        binding.loginNickNameEditText.addTextChangedListener {
            it?.let { nickname -> inputViewModel.saveNickname(nickname.toString()) }
            val emailFlag = flagViewModel.constraintFlag.value?.emailConstraintFlag ?: false
            when {
                it.isNullOrBlank() -> {
                    resetError(binding.loginNickNameLayout)
                    flagViewModel.setFlag(false, emailFlag)
                }
                Utils.checkNicknameConstraint(it.toString()) -> {
                    resetError(binding.loginNickNameLayout)
                    flagViewModel.setFlag(true, emailFlag)
                }
                else -> {
                    setNicknameError(binding.loginNickNameLayout)
                    flagViewModel.setFlag(false, emailFlag)
                }
            }
        }
        binding.loginEmailEditText.addTextChangedListener {
            it?.let { email -> inputViewModel.saveEmail(email.toString()) }
            val nicknameFlag = flagViewModel.constraintFlag.value?.nicknameConstraintFlag ?: false
            when {
                it.isNullOrBlank() -> {
                    resetError(binding.loginEmailLayout)
                    flagViewModel.setFlag(nicknameFlag, false)
                }
                Utils.checkEmailConstraint(it.toString()) -> {
                    resetError(binding.loginEmailLayout)
                    flagViewModel.setFlag(nicknameFlag, true)
                }
                else -> {
                    setEmailError(binding.loginEmailLayout)
                    flagViewModel.setFlag(nicknameFlag, false)
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
        flagViewModel.constraintFlag.observe(this) { flag ->
            binding.loginButton.isEnabled = flag.nicknameConstraintFlag && flag.emailConstraintFlag
        }
    }
}