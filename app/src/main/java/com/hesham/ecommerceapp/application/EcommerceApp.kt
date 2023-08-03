package com.hesham.ecommerceapp.application

import android.app.Application
import com.hesham.ecommerceapp.di.applicationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EcommerceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }
    private fun initDi() {
        startKoin {
            androidContext(this@EcommerceApp)
            modules(applicationModules)
        }
    }
}