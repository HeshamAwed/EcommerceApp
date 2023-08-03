package com.hesham.ecommerceapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.databinding.ItemCategoryTabBinding

class CategoryAdapter(
    private val categories: List<String>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var selectedCategory = 0

    inner class ViewHolder(private val binding: ItemCategoryTabBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            binding.textCategoryTitle.text = item
            binding.textCategoryTitle.background = ContextCompat.getDrawable(
                itemView.context,
                if (selectedCategory == position) R.drawable.bg_categories_tabs_selected else R.drawable.bg_categories_tabs_unselected
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryTabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(categories[position])
            selectedCategory = position
            notifyDataSetChanged()
        }
        holder.bind(categories[position], position)
    }
}