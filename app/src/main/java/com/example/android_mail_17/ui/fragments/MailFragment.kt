package com.example.android_mail_17.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_mail_17.R
import com.example.android_mail_17.databinding.FragmentMailBinding
import com.example.android_mail_17.ui.activities.HomeActivity

class MailFragment : Fragment() {
    private var _binding: FragmentMailBinding? = null
    private val binding: FragmentMailBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAppBar()
    }

    private fun setAppBar() {
        (activity as HomeActivity).setAppBar(R.drawable.menu, R.string.mail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}