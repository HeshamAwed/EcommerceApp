package com.hesham.ecommerceapp.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.databinding.ItemCategoryTabBinding
import com.hesham.ecommerceapp.databinding.ItemListCartBinding
import com.hesham.ecommerceapp.databinding.ItemListProductBinding
import com.hesham.ecommerceapp.domain.entities.Product

class CartAdapter(
    private val products: List<Product>,
    private val onItemClicked: (Product) -> Unit,
    private val onAddToCartClicked: (Product) -> Unit,
    private val onRemoveFromCartClicked: (Product) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var productsFilteredList = ArrayList<Product>()

    init {
        productsFilteredList.addAll(products)
    }

    inner class ViewHolder(val binding: ItemListCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Product) {
            binding.imageProduct.load(item.image)
            binding.textName.text = item.title
            binding.textPrice.text = "${item.price} $"
            binding.textQty.text = "${item.quantity}"
            binding.textItemTotalPrice.text = "${item.price * item.quantity} $"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productsFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(products[position])
        }
        holder.binding.buttonPlus.setOnClickListener {
            onAddToCartClicked(products[position])
        }
        holder.binding.buttonMinus.setOnClickListener {
            onRemoveFromCartClicked(products[position])
        }
        holder.bind(products[position])
    }
}