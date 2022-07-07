package com.example.android_mail_17.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.android_mail_17.R
import com.example.android_mail_17.databinding.ActivityHomeBinding
import com.example.android_mail_17.ui.fragments.MailFragment
import com.example.android_mail_17.ui.fragments.SettingFragment

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    val binding: ActivityHomeBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationView()
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