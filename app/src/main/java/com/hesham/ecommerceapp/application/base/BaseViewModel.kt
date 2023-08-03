package com.hesham.ecommerceapp.application.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hesham.ecommerceapp.utils.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    val loadingLiveData: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val errorLiveData: SingleLiveEvent<Throwable> = SingleLiveEvent()

}