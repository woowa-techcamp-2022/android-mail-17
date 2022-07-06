package com.example.android_mail_17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_mail_17.databinding.ActivityLoginBinding
import com.example.android_mail_17.models.ConstraintFlag
import com.example.android_mail_17.others.Utils
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    val binding: ActivityLoginBinding get() = requireNotNull(_binding)
    private var _constraintFlag = MutableLiveData<ConstraintFlag>()
    val constraintFlag: LiveData<ConstraintFlag> get() = _constraintFlag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextChangeListener()
        setLiveDataObserver()
    }

    private fun setTextChangeListener() {
        binding.loginNickNameEditText.addTextChangedListener {
            val emailFlag = _constraintFlag.value?.emailConstraintFlag ?: false
            when {
                it.isNullOrBlank() -> {
                    resetError(binding.loginNickNameLayout)
                    setFlag(false, emailFlag)
                }
                Utils.checkNicknameConstraint(it.toString()) -> {
                    resetError(binding.loginNickNameLayout)
                    setFlag(true, emailFlag)
                }
                else -> {
                    setNicknameError(binding.loginNickNameLayout)
                    setFlag(false, emailFlag)
                }
            }
        }
        binding.loginEmailEditText.addTextChangedListener {
            val nicknameFlag = _constraintFlag.value?.nicknameConstraintFlag ?: false
            when {
                it.isNullOrBlank() -> {
                    resetError(binding.loginEmailLayout)
                    setFlag(nicknameFlag, false)
                }
                Utils.checkEmailConstraint(it.toString()) -> {
                    resetError(binding.loginEmailLayout)
                    setFlag(nicknameFlag, true)
                }
                else -> {
                    setEmailError(binding.loginEmailLayout)
                    setFlag(nicknameFlag, false)
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

    private fun setFlag(nicknameFlag: Boolean, emailFlag: Boolean) {
        _constraintFlag.postValue(ConstraintFlag(nicknameFlag, emailFlag))
    }

    private fun setLiveDataObserver() {
        constraintFlag.observe(this) { flag ->
            binding.loginButton.isEnabled = flag.nicknameConstraintFlag && flag.emailConstraintFlag
        }
    }
}