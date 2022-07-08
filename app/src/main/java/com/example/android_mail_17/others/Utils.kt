package com.example.android_mail_17.others

import android.content.Context
import android.util.Patterns
import com.example.android_mail_17.R
import kotlin.random.Random

object Utils {
    fun checkNicknameConstraint(nickname: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9]*\$")
        return regex.matches(nickname) && nickname.length in 4..12
    }

    fun checkEmailConstraint(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun getRandomColor(context: Context): Int {
        val colorArr = context.resources.getIntArray(R.array.profile_background_color)
        return colorArr[Random.nextInt(colorArr.size)]
    }
}