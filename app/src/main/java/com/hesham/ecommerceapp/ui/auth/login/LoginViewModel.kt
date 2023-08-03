package com.hesham.ecommerceapp.ui.auth.login

import androidx.lifecycle.LiveData
import com.hesham.ecommerceapp.application.base.BaseViewModel
import com.hesham.ecommerceapp.domain.entities.DtoLogin
import com.hesham.ecommerceapp.domain.entities.UserToken
import com.hesham.ecommerceapp.domain.repositories.AuthRepository
import com.hesham.ecommerceapp.ui.ktx.launchDataLoad
import com.hesham.ecommerceapp.utils.SingleLiveEvent

class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    private val _loginResultLiveData: SingleLiveEvent<UserToken> = SingleLiveEvent()
    val loginResultLiveData: LiveData<UserToken> = _loginResultLiveData

    fun login(username: String, password: String) {
        launchDataLoad(
            execution = {
                val loginResult = authRepository.login(DtoLogin(username, password))
                 authRepository.setToken(loginResult.token)
                _loginResultLiveData.postValue(loginResult)
            },
        )
    }

}