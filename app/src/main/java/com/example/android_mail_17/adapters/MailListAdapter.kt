package com.example.android_mail_17.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mail_17.databinding.ViewMailListBinding

class MailListAdapter : RecyclerView.Adapter<MailListAdapter.MailListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MailListViewHolder {
        val binding = ViewMailListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MailListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MailListViewHolder, position: Int) {
        // TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = 14

    inner class MailListViewHolder(binding: ViewMailListBinding) :
        RecyclerView.ViewHolder(binding.root)
}