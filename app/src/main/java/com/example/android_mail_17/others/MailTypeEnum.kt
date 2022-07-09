package com.example.android_mail_17.others

import com.google.gson.annotations.SerializedName

enum class MailTypeEnum {
    @SerializedName("primary")
    PRIMARY,
    @SerializedName("social")
    SOCIAL,
    @SerializedName("promotion")
    PROMOTION
}