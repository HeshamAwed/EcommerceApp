package com.hesham.ecommerceapp.ui.auth.signup

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.application.base.BaseViewModel
import com.hesham.ecommerceapp.domain.entities.DtoLogin
import com.hesham.ecommerceapp.domain.entities.Name
import com.hesham.ecommerceapp.domain.entities.User
import com.hesham.ecommerceapp.domain.entities.UserId
import com.hesham.ecommerceapp.domain.entities.UserToken
import com.hesham.ecommerceapp.domain.repositories.AuthRepository
import com.hesham.ecommerceapp.ui.ktx.launchDataLoad
import com.hesham.ecommerceapp.utils.SingleLiveEvent

class SignupViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    private val _signUpResultLiveData: SingleLiveEvent<UserId> = SingleLiveEvent()
    val signUpResultLiveData: LiveData<UserId> = _signUpResultLiveData

    private val _validationResultLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val validationResultLiveData: LiveData<String> = _validationResultLiveData

    private fun signup(user: User) {
        launchDataLoad(
            execution = {
                val signupResult = authRepository.signUp(user)
                authRepository.setUserId(signupResult.id)
                _signUpResultLiveData.postValue(signupResult)
            },
        )
    }

    fun validateUserData(
        context: Context,
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        var isValid = true
        val names = fullName.split(" ")
        if (names.size < 2) {
            _validationResultLiveData.postValue(context.getString(R.string.please_enter_a_valid_full_name))
            isValid = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _validationResultLiveData.postValue(context.getString(R.string.please_enter_a_valid_email))
            isValid = false
        }
        if (password.isEmpty()) {
            _validationResultLiveData.postValue(context.getString(R.string.please_enter_the_password))
            isValid = false
        }
        if (password != confirmPassword) {
            _validationResultLiveData.postValue(context.getString(R.string.the_password_not_equal_to_the_confirm_password))
            isValid = false
        }
        if (isValid) {
            val username = email.split("@")
            val user = User(null, email, 0, Name(names[0], names[1]), password, "", username[0])
            signup(user)
        }
    }

}