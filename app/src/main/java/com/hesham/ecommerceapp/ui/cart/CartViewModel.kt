package com.hesham.ecommerceapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hesham.ecommerceapp.application.base.BaseViewModel
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.domain.repositories.CartRepository
import com.hesham.ecommerceapp.ui.ktx.launchDataLoad
import com.hesham.ecommerceapp.utils.SingleLiveEvent

class CartViewModel(private val cartRepository: CartRepository) : BaseViewModel() {

    private val _productsLiveData: SingleLiveEvent<List<Product>> = SingleLiveEvent()
    val productsLiveData: LiveData<List<Product>> = _productsLiveData

    private val _quantityLiveData: SingleLiveEvent<Int> = SingleLiveEvent()
    val quantityLiveData: LiveData<Int> = _quantityLiveData


    fun getCart() {
        _productsLiveData.postValue(cartRepository.getCart())
    }

    fun addToCart(product: Product) {
        launchDataLoad(
            execution = {
                _quantityLiveData.postValue(cartRepository.addToCart(product))
            }
        )
    }

    fun removeFromCart(product: Product) {
        launchDataLoad(
            execution = {
                _quantityLiveData.postValue(cartRepository.removeFromCart(product))
            }
        )
    }
}