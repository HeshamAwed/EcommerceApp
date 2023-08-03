package com.hesham.ecommerceapp.ui.ktx

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hesham.ecommerceapp.application.base.BaseViewModel
import com.hesham.ecommerceapp.domain.entities.handleHttpExceptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun BaseViewModel.launchDataLoad(
    execution: suspend CoroutineScope.() -> Unit,
    errorReturned: (suspend CoroutineScope.(Throwable) -> Unit)? = null,
    finallyBlock: (suspend CoroutineScope.() -> Unit)? = null
) {
    this.viewModelScope.launch {
        loadingLiveData.postValue(true)
        try {
            execution()
        } catch (e: Throwable) {
            val error = handleHttpExceptions(e)
            errorLiveData.postValue(error)
            errorReturned?.invoke(this,error)
        } finally {
            loadingLiveData.postValue(false)
            finallyBlock?.invoke(this)
        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        //for other device how are able to connect with Ethernet
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        //for check internet over Bluetooth
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

