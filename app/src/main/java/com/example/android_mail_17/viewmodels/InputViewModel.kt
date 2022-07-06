package com.example.android_mail_17.viewmodels

import androidx.lifecycle.ViewModel

class InputViewModel : ViewModel() {
    private var _nickname = ""
    val nickname get() = _nickname
    private var _email = ""
    val email get() = _email

    fun saveNickname(nickname: String) {
        _nickname = nickname
    }

    fun saveEmail(email: String) {
        _email = email
    }
}