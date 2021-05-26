package com.example.login_contact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.login_contact.databinding.ContactInfoLayoutBinding
import com.example.login_contact.db.entities.CategoryContactEntity


class ContactAdapter(val category :String): ListAdapter<CategoryContactEntity,ContactAdapter.ViewHolder>(CategoryContactDiffUtil()) {



    inner class ViewHolder(val binding: ContactInfoLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bindItem (contact: CategoryContactEntity){
            binding.contactNameTextview.text = contact.name

            binding.contactNumberTextview.text = contact.number
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContactInfoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if(item.categoryName == category){
            holder.bindItem(item)
        }


    }
    class CategoryContactDiffUtil: DiffUtil.ItemCallback<CategoryContactEntity>() {
        override fun areItemsTheSame(
            oldItem: CategoryContactEntity,
            newItem: CategoryContactEntity
        ): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CategoryContactEntity,
            newItem: CategoryContactEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }


    }


}