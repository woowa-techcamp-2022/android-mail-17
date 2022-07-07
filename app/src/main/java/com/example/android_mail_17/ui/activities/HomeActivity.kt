package com.example.android_mail_17.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.android_mail_17.R
import com.example.android_mail_17.databinding.ActivityHomeBinding
import com.example.android_mail_17.ui.fragments.MailFragment
import com.example.android_mail_17.ui.fragments.SettingFragment

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding: ActivityHomeBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAppBarMenuClickListener()
        setDrawerItemClickListener()
        setBottomNavigationView()
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
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setBottomNavigationView() {
        binding.bottomNavigation.run {
            setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.bottomNavigationMailMenu -> changeFragment(MailFragment())
                    R.id.bottomNavigationSettingMenu -> changeFragment(SettingFragment())
                }
                true
            }
            selectedItemId = R.id.bottomNavigationMailMenu
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}