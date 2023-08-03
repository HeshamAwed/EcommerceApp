package com.hesham.ecommerceapp.ui.home

import com.hesham.ecommerceapp.ui.cart.CartViewModel
import com.hesham.ecommerceapp.ui.main.MainViewModel
import com.hesham.ecommerceapp.ui.product_details.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun homeModule() = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ProductDetailsViewModel(get()) }
    viewModel { CartViewModel(get()) }
    viewModel { MainViewModel(get()) }
}