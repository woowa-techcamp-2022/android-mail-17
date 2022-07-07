package com.example.android_mail_17.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android_mail_17.models.EmailData
import com.example.android_mail_17.others.MailTypeEnum
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmailViewModel(application: Application) : AndroidViewModel(application) {
    private var _mailType = MutableLiveData<MailTypeEnum>()
    val mailType: MutableLiveData<MailTypeEnum> get() = _mailType
    private var _emailData = MutableLiveData<List<EmailData>>()
    val emailData: MutableLiveData<List<EmailData>> get() = _emailData

    init {
        fetchData(application)
        setMailType(MailTypeEnum.PRIMARY)
    }


    private fun fetchData(context: Context) {
        val jsonStr = context.assets.open("dummy.json").reader().readText()
        val listType = object : TypeToken<MutableList<EmailData>>() {}.type
        val data: List<EmailData> = Gson().fromJson(jsonStr, listType)
        saveEmailData(data)
    }

    private fun saveEmailData(data: List<EmailData>) {
        _emailData.postValue(data)
    }

    fun setMailType(type: MailTypeEnum) {
        mailType.postValue(type)
    }

    fun getFilteredDataByType(type: MailTypeEnum): List<EmailData>? {
        return when (type) {
            MailTypeEnum.PRIMARY -> emailData.value?.filter { it.type == "primary" }
            MailTypeEnum.SOCIAL -> emailData.value?.filter { it.type == "social" }
            MailTypeEnum.PROMOTION -> emailData.value?.filter { it.type == "promotion" }
        }
    }
}