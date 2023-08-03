package com.hesham.ecommerceapp.ui.auth

import com.hesham.ecommerceapp.ui.auth.login.LoginViewModel
import com.hesham.ecommerceapp.ui.profile.ProfileViewModel
import com.hesham.ecommerceapp.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun authModule() = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}