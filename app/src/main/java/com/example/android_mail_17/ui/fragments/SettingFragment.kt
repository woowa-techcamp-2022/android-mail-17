package com.example.android_mail_17.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.android_mail_17.R
import com.example.android_mail_17.databinding.FragmentSettingBinding
import com.example.android_mail_17.ui.activities.HomeActivity
import com.example.android_mail_17.viewmodels.InputViewModel

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = requireNotNull(_binding)
    private val inputViewModel by activityViewModels<InputViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBar()
        setInformation()
    }

    private fun setAppBar() {
        (activity as HomeActivity).setAppBar(null, R.string.setting)
    }

    private fun setInformation() {
        binding.nickNameView.text = inputViewModel.nickname
        binding.emailView.text = inputViewModel.email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}