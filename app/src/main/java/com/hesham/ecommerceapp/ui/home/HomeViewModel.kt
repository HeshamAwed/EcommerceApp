package com.hesham.ecommerceapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hesham.ecommerceapp.application.base.BaseFragment
import com.hesham.ecommerceapp.application.base.BaseViewModel
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.domain.entities.UserToken
import com.hesham.ecommerceapp.domain.repositories.ProductRepository
import com.hesham.ecommerceapp.ui.ktx.launchDataLoad
import com.hesham.ecommerceapp.utils.SingleLiveEvent

class HomeViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    private val _categoriesLiveData: SingleLiveEvent<List<String>> = SingleLiveEvent()
    val categoriesLiveData: LiveData<List<String>> = _categoriesLiveData

    private val _productsLiveData: SingleLiveEvent<List<Product>> = SingleLiveEvent()
    val productsLiveData: LiveData<List<Product>> = _productsLiveData


    fun getAllCategories() {
        launchDataLoad(
            execution = {
                _categoriesLiveData.postValue(productRepository.getAllCategories())
            }
        )
    }

    fun getProductsByCategory(category: String) {
        launchDataLoad(
            execution = {
                _productsLiveData.postValue(productRepository.getProductsByCategory(category))
            }
        )
    }
}