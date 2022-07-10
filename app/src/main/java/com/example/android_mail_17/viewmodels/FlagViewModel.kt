package com.example.android_mail_17.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlagViewModel : ViewModel() {
    private var _nicknameConstraintFlag = MutableLiveData(false)
    private var _emailConstraintFlag = MutableLiveData(false)
    val buttonEnabledFlag
        get() = MediatorLiveData<Boolean>().apply {
            addSource(_nicknameConstraintFlag) { value = isButtonEnabled() }
            addSource(_emailConstraintFlag) { value = isButtonEnabled() }
        }

    private fun isButtonEnabled(): Boolean {
        return _nicknameConstraintFlag.value ?: false && _emailConstraintFlag.value ?: false
    }

    fun setNicknameFlag(flag: Boolean) {
        _nicknameConstraintFlag.value = flag
    }

    fun setEmailFlag(flag: Boolean) {
        _emailConstraintFlag.value = flag
    }
}