package com.hesham.ecommerceapp.ui.home.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.databinding.ItemCategoryTabBinding
import com.hesham.ecommerceapp.databinding.ItemListProductBinding
import com.hesham.ecommerceapp.domain.entities.Product

class ProductsAdapter(
    private val products: ArrayList<Product>,
    private val onItemClicked: (Product) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>(), Filterable {
    var productsFilteredList = ArrayList<Product>()

    init {
        productsFilteredList.addAll(products)
    }

    inner class ViewHolder(private val binding: ItemListProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
        fun bind(item: Product) {
            binding.imageProduct.load(item.image)
            binding.textName.text = item.title
            binding.textCategory.text = item.category
            binding.textPrice.text = "${item.price} $"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productsFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(productsFilteredList[position])
        }
        holder.bind(productsFilteredList[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val word = constraint.toString()
                productsFilteredList = if (word.isEmpty()) {
                   products
                } else {
                    val resultList = ArrayList<Product>()
                    products.forEach {
                        val title = it.title.lowercase()
                        if (title.contains(word.lowercase())) {
                            resultList.add(it)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = productsFilteredList
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productsFilteredList = results?.values as ArrayList<Product>
                notifyDataSetChanged()
            }


        }
    }

}