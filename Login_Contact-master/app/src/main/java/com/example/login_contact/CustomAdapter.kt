package com.example.login_contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.login_contact.databinding.CategoryItemLayoutBinding
import com.example.login_contact.db.entities.CategoryEntiity

class CustomAdapter(val listener:OnitemClickListener, val owner: String): ListAdapter<CategoryEntiity, CustomAdapter.ViewHolder>(categoryDiffUtil()) {



    inner class ViewHolder(private val binding: CategoryItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{

            fun bindItem(categoryEntiity: CategoryEntiity){
            binding.categoryTextView.text = categoryEntiity.category
        }
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val category = getItem(adapterPosition)
            listener.onItemClicked(category)
        }

    }

    interface OnitemClickListener{
        fun onItemClicked(category: CategoryEntiity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)

        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        if (category.owner == this.owner){
            holder.bindItem(category)
        }

    }
class categoryDiffUtil: DiffUtil.ItemCallback<CategoryEntiity>(){
    override fun areItemsTheSame(oldItem: CategoryEntiity, newItem: CategoryEntiity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoryEntiity, newItem: CategoryEntiity): Boolean {
        return oldItem.id == newItem.id && oldItem.owner == newItem.owner
    }

}



}