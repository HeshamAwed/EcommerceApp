package com.hesham.ecommerceapp.ui.product_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hesham.ecommerceapp.databinding.ItemBackdropImageBinding

class ImagePagerAdapter(private val itemList: ArrayList<String>) :
    RecyclerView.Adapter<ImagePagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemBackdropImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.imageBackdrop.load(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBackdropImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}