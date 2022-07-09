package com.example.android_mail_17.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_mail_17.models.EmailData
import com.example.android_mail_17.others.MailTypeEnum
import com.example.android_mail_17.others.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EmailViewModel(application: Application) : AndroidViewModel(application) {
    private var _emailData = MutableLiveData<List<EmailData>>()
    private val emailData: LiveData<List<EmailData>> get() = _emailData

    init {
        fetchData(application)
    }


    private fun fetchData(context: Context) {
        try {
            val jsonStr = context.assets.open("dummy.json").reader().readText()
            val listType = object : TypeToken<MutableList<EmailData>>() {}.type
            val data: List<EmailData> = Gson().fromJson(jsonStr, listType)
            data.forEach { it.color = Utils.getRandomColor(context) }
            _emailData.value = data
            context.assets.close()
        } catch (e: Exception) {
            Log.e("userAssets", "${e.message}")
        }
    }

    fun getFilteredDataByType(type: MailTypeEnum): List<EmailData>? {
        return when (type) {
            MailTypeEnum.PRIMARY -> emailData.value?.filter { it.type == MailTypeEnum.PRIMARY }
            MailTypeEnum.SOCIAL -> emailData.value?.filter { it.type == MailTypeEnum.SOCIAL }
            MailTypeEnum.PROMOTION -> emailData.value?.filter { it.type == MailTypeEnum.PROMOTION }
        }
    }
}