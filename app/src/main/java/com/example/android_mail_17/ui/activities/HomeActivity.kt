package com.example.android_mail_17.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.android_mail_17.R
import com.example.android_mail_17.databinding.ActivityHomeBinding
import com.example.android_mail_17.others.MailTypeEnum
import com.example.android_mail_17.ui.fragments.MailFragment
import com.example.android_mail_17.ui.fragments.SettingFragment
import com.example.android_mail_17.viewmodels.InputViewModel
import com.example.android_mail_17.viewmodels.MenuViewModel
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val inputViewModel by viewModels<InputViewModel>()
    private val menuViewModel by viewModels<MenuViewModel>()

    companion object {
        const val NICKNAME = "Nickname"
        const val EMAIL = "Email"

        fun getIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setInputInfo()
        setObservers()
        setAppBarMenuClickListener()
        setDrawerItemClickListener()
        setNavigationBarView()
    }

    private fun setInputInfo() {
        intent.extras?.run {
            inputViewModel.saveNickname(getString(NICKNAME, "nickname"))
            inputViewModel.saveEmail(getString(EMAIL, "email"))
        }
    }

    private fun setObservers() {
        menuViewModel.selectedTab.observe(this) {
            setNavigationViewMenu(it)
            when (it) {
                R.id.mailMenu -> changeFragment(MailFragment())
                else -> changeFragment(SettingFragment())
            }
        }
        menuViewModel.selectedMailType.observe(this) { setDrawerMenu(it) }
    }

    private fun setNavigationViewMenu(menuId: Int) {
        binding.bottomNavigation?.run { menu.findItem(menuId).isChecked = true }
        binding.navigationRail?.run { menu.findItem(menuId).isChecked = true }
    }

    private fun setDrawerMenu(mailType: MailTypeEnum) {
        with(binding.drawer.menu) {
            when (mailType) {
                MailTypeEnum.PRIMARY -> findItem(R.id.drawerPrimaryMenu).isChecked = true
                MailTypeEnum.SOCIAL -> findItem(R.id.drawerSocialMenu).isChecked = true
                MailTypeEnum.PROMOTION -> findItem(R.id.drawerPromotionMenu).isChecked = true
            }
        }
    }

    fun setAppBar(icon: Int?, title: Int) {
        binding.appBar.run {
            if (icon == null) {
                navigationIcon = null
            } else {
                setNavigationIcon(icon)
            }
            setTitle(title)
        }
    }

    private fun setAppBarMenuClickListener() {
        binding.appBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setDrawerItemClickListener() {
        binding.drawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.drawerPrimaryMenu -> menuViewModel.setSelectedMailType(MailTypeEnum.PRIMARY)
                R.id.drawerSocialMenu -> menuViewModel.setSelectedMailType(MailTypeEnum.SOCIAL)
                R.id.drawerPromotionMenu -> menuViewModel.setSelectedMailType(MailTypeEnum.PROMOTION)
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setNavigationBarView() {
        NavigationBarView.OnItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.mailMenu) {
                setMailTabDefault()
            }
            menuViewModel.setSelectedTab(menuItem.itemId)
            true
        }.let { listener ->
            binding.bottomNavigation?.setOnItemSelectedListener(listener)
            binding.navigationRail?.setOnItemSelectedListener(listener)
        }
    }

    private fun setMailTabDefault() {
        menuViewModel.run {
            setSelectedTab(R.id.mailMenu)
            setSelectedMailType(MailTypeEnum.PRIMARY)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        when (menuViewModel.selectedTab.value) {
            R.id.mailMenu -> {
                when (menuViewModel.selectedMailType.value) {
                    MailTypeEnum.PRIMARY -> super.onBackPressed()
                    else -> menuViewModel.setSelectedMailType(MailTypeEnum.PRIMARY)
                }
            }
            else -> setMailTabDefault()
        }
    }
}