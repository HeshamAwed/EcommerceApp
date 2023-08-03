package com.hesham.ecommerceapp.ui.product_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hesham.ecommerceapp.application.base.BaseViewModel
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.domain.repositories.CartRepository
import com.hesham.ecommerceapp.ui.ktx.launchDataLoad
import com.hesham.ecommerceapp.utils.SingleLiveEvent

class ProductDetailsViewModel(private val cartRepository: CartRepository) : BaseViewModel() {
    private val _productLiveData: SingleLiveEvent<Product> = SingleLiveEvent()
    val productLiveData: LiveData<Product> = _productLiveData
    lateinit var product: Product
    private val _quantityLiveData: SingleLiveEvent<Int> = SingleLiveEvent()
    val quantityLiveData: LiveData<Int> = _quantityLiveData

    fun initProduct(product: Product) {
        this.product = product
        _productLiveData.postValue(product)
        _quantityLiveData.postValue(cartRepository.getProductQuantityFromCart(product.id))
    }

    fun addToCart() {
        launchDataLoad(
            execution = {
                _quantityLiveData.postValue(cartRepository.addToCart(product))
            }
        )
    }

    fun removeFromCart() {
        launchDataLoad(
            execution = {
                _quantityLiveData.postValue(cartRepository.removeFromCart(product))
            }
        )
    }
}