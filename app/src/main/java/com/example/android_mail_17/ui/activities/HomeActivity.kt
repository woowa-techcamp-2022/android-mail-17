package com.example.android_mail_17.ui.activities

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
import com.example.android_mail_17.viewmodels.EmailViewModel
import com.example.android_mail_17.viewmodels.InputViewModel

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding: ActivityHomeBinding get() = requireNotNull(_binding)
    private val inputViewModel by viewModels<InputViewModel>()
    private val emailViewModel by viewModels<EmailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.run {
            inputViewModel.saveNickname(getString("nickname", "nickname"))
            inputViewModel.saveEmail(getString("email", "email"))
        }

        setAppBarMenuClickListener()
        setDrawerItemClickListener()
        setBottomNavigationView(savedInstanceState?.getInt("selectedItemId"))
        setNavigationRail(savedInstanceState?.getInt("selectedItemId"))
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
                R.id.drawerPrimaryMenu -> emailViewModel.setMailType(MailTypeEnum.PRIMARY)
                R.id.drawerSocialMenu -> emailViewModel.setMailType(MailTypeEnum.SOCIAL)
                R.id.drawerPromotionMenu -> emailViewModel.setMailType(MailTypeEnum.PROMOTION)
            }
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setBottomNavigationView(itemId: Int?) {
        binding.bottomNavigation?.run {
            setOnItemSelectedListener { menuItem ->
                checkMenuItemId(menuItem.itemId)
                true
            }
            selectedItemId = itemId ?: R.id.mailMenu
        }
    }

    private fun setNavigationRail(itemId: Int?) {
        binding.navigationRail?.run {
            setOnItemSelectedListener { menuItem ->
                checkMenuItemId(menuItem.itemId)
                true
            }
            selectedItemId = itemId ?: R.id.mailMenu
        }
    }

    private fun checkMenuItemId(itemId: Int) {
        when (itemId) {
            R.id.mailMenu -> {
                resetMailTab()
                changeFragment(MailFragment())
            }
            R.id.settingMenu -> changeFragment(SettingFragment())
        }
    }

    private fun resetMailTab() {
        binding.drawer.setCheckedItem(R.id.drawerPrimaryMenu)
        emailViewModel.setMailType(MailTypeEnum.PRIMARY)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        binding.bottomNavigation?.run { outState.putInt("selectedItemId", selectedItemId) }
        binding.navigationRail?.run { outState.putInt("selectedItemId", selectedItemId) }
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        binding.bottomNavigation?.run {
            when (selectedItemId) {
                R.id.mailMenu -> {
                    when (emailViewModel.mailType.value) {
                        MailTypeEnum.PRIMARY -> super.onBackPressed()
                        else -> resetMailTab()
                    }
                }
                R.id.settingMenu -> selectedItemId = R.id.mailMenu
            }
        }

        binding.navigationRail?.run {
            when (selectedItemId) {
                R.id.mailMenu -> {
                    when (emailViewModel.mailType.value) {
                        MailTypeEnum.PRIMARY -> super.onBackPressed()
                        else -> resetMailTab()
                    }
                }
                R.id.settingMenu -> selectedItemId = R.id.mailMenu
            }
        }
    }
}