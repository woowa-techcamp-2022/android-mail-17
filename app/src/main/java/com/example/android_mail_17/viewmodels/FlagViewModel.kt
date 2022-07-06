package com.example.android_mail_17.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_mail_17.models.ConstraintFlag

class FlagViewModel : ViewModel() {
    private var _constraintFlag = MutableLiveData<ConstraintFlag>()
    val constraintFlag: LiveData<ConstraintFlag> get() = _constraintFlag

    init {
        _constraintFlag.postValue(
            ConstraintFlag(
                nicknameConstraintFlag = false,
                emailConstraintFlag = false
            )
        )
    }

    fun setFlag(nicknameFlag: Boolean, emailFlag: Boolean) {
        _constraintFlag.postValue(ConstraintFlag(nicknameFlag, emailFlag))
    }
}