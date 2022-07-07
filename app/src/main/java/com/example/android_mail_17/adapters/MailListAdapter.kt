package com.example.android_mail_17.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android_mail_17.R
import com.example.android_mail_17.databinding.ViewMailListImageProfileBinding
import com.example.android_mail_17.databinding.ViewMailListTextProfileBinding
import com.example.android_mail_17.models.EmailData
import com.example.android_mail_17.others.Utils

class MailListAdapter : RecyclerView.Adapter<MailListAdapter.MailListViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<EmailData>() {
        override fun areItemsTheSame(oldItem: EmailData, newItem: EmailData): Boolean {
            return oldItem.nickname == newItem.nickname
        }

        override fun areContentsTheSame(oldItem: EmailData, newItem: EmailData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MailListViewHolder {
        return if (viewType == 0) {
            val binding = ViewMailListTextProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MailListViewHolder.TextProfileViewHolder(binding)
        } else {
            val binding = ViewMailListImageProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MailListViewHolder.ImageProfileViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: MailListViewHolder, position: Int) {
        when (holder) {
            is MailListViewHolder.TextProfileViewHolder -> holder.bind(differ.currentList[position])
            is MailListViewHolder.ImageProfileViewHolder -> holder.bind(differ.currentList[position])
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position].nickname[0]) {
            in 'a'..'z' -> 0
            in 'A'..'Z' -> 0
            else -> 1
        }
    }

    sealed class MailListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class TextProfileViewHolder(
            private val binding: ViewMailListTextProfileBinding
        ) : MailListViewHolder(binding.root) {
            fun bind(data: EmailData) {
                setTextProfile(data.nickname[0].toString())
                binding.mailNicknameView.text = data.nickname
                binding.mailTitleView.text = data.title
                binding.mailBodyView.text = data.body
            }

            private fun setTextProfile(firstWord: String) {
                binding.mailTextProfile.run {
                    text = firstWord
                    setBackgroundColor(Utils.getRandomColor(binding.root.context))
                }
            }
        }

        class ImageProfileViewHolder(
            private val binding: ViewMailListImageProfileBinding
        ) : MailListViewHolder(binding.root) {
            fun bind(data: EmailData) {
                binding.mailImageProfile.setImageDrawable(
                    binding.root.context.getDrawable(R.drawable.profile)
                )
                binding.mailNicknameView.text = data.nickname
                binding.mailTitleView.text = data.title
                binding.mailBodyView.text = data.body
            }
        }
    }
}