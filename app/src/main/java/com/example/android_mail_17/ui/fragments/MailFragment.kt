package com.example.android_mail_17.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.android_mail_17.R
import com.example.android_mail_17.adapters.MailListAdapter
import com.example.android_mail_17.databinding.FragmentMailBinding
import com.example.android_mail_17.others.MailTypeEnum
import com.example.android_mail_17.ui.activities.HomeActivity
import com.example.android_mail_17.viewmodels.EmailViewModel

class MailFragment : Fragment() {
    private var _binding: FragmentMailBinding? = null
    private val binding: FragmentMailBinding get() = requireNotNull(_binding)
    private val adapter: MailListAdapter by lazy { MailListAdapter() }
    private val emailViewModel by activityViewModels<EmailViewModel>()

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
        setRecyclerView()
        setDataObserver()
    }

    private fun setAppBar() {
        (activity as HomeActivity).setAppBar(R.drawable.menu, R.string.mail)
    }

    private fun setRecyclerView() {
        binding.mailRecyclerView.adapter = adapter
    }

    private fun setDataObserver() {
        emailViewModel.mailType.observe(viewLifecycleOwner) { type ->
            type?.run {
                when (this) {
                    MailTypeEnum.PRIMARY -> binding.mailTypeView.text = getString(R.string.primary)
                    MailTypeEnum.SOCIAL -> binding.mailTypeView.text = getString(R.string.social)
                    MailTypeEnum.PROMOTION -> binding.mailTypeView.text =
                        getString(R.string.promotion)
                }
                adapter.differ.submitList(emailViewModel.getFilteredDataByType(this))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}