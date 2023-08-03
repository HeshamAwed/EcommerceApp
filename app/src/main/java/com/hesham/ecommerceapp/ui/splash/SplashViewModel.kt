package com.hesham.ecommerceapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hesham.ecommerceapp.domain.repositories.AuthRepository
import com.hesham.ecommerceapp.utils.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _isLoggedInLiveData: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isLoggedInLiveData: LiveData<Boolean> = _isLoggedInLiveData

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoggedInLiveData.postValue(authRepository.getToken().isNotEmpty())
        }
    }

}