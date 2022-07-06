package com.example.android_mail_17.others

import android.util.Patterns

object Utils {
    fun checkNicknameConstraint(nickname: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9]*\$")
        return regex.matches(nickname) && nickname.length in 4..12
    }

    fun checkEmailConstraint(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}