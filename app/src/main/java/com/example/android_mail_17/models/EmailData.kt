package com.example.android_mail_17.models

import com.example.android_mail_17.others.MailTypeEnum

data class EmailData(
    val nickname: String,
    val title: String,
    val body: String,
    val type: MailTypeEnum,
    var color: Int?
)
