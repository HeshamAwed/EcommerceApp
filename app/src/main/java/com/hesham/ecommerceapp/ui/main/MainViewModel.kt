package com.hesham.ecommerceapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hesham.ecommerceapp.application.base.BaseViewModel
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.domain.repositories.CartRepository
import com.hesham.ecommerceapp.ui.ktx.launchDataLoad
import com.hesham.ecommerceapp.utils.SingleLiveEvent

class MainViewModel(private val cartRepository: CartRepository) : BaseViewModel() {


    fun getTotalProductAdded() = cartRepository.getTotalProductAdded()
}