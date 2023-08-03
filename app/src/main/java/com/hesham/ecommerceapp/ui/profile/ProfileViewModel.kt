package com.hesham.ecommerceapp.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hesham.ecommerceapp.application.base.BaseViewModel
import com.hesham.ecommerceapp.domain.entities.User
import com.hesham.ecommerceapp.domain.repositories.AuthRepository
import com.hesham.ecommerceapp.ui.ktx.launchDataLoad
import com.hesham.ecommerceapp.utils.SingleLiveEvent

class ProfileViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    private val _logoutResultLiveData: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val logoutResultLiveData: LiveData<Boolean> = _logoutResultLiveData

    private val _userResultLiveData: SingleLiveEvent<User> = SingleLiveEvent()
    val userResultLiveData: LiveData<User> = _userResultLiveData


    fun getUserData(){
        launchDataLoad(
            execution = {
                _userResultLiveData.postValue(authRepository.getUser())
            }
        )
    }
    fun logout() {
        authRepository.logout()
        _logoutResultLiveData.postValue(true)
    }


}