package com.example.android_mail_17.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_mail_17.R
import com.example.android_mail_17.others.MailTypeEnum

class MenuViewModel : ViewModel() {
    private var _selectedTab = MutableLiveData(R.id.mailMenu)
    val selectedTab: LiveData<Int> get() = _selectedTab

    private var _selectedMailType = MutableLiveData(MailTypeEnum.PRIMARY)
    val mailType: LiveData<MailTypeEnum> get() = _selectedMailType

    fun setSelectedTab(menu: Int) {
        _selectedTab.value = menu
    }

    fun setMailType(type: MailTypeEnum) {
        _selectedMailType.value = type
    }
}