package com.hesham.ecommerceapp.application

import android.app.Application
import android.content.Context
import com.hesham.ecommerceapp.di.applicationModules
import com.hesham.ecommerceapp.utils.LocalUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EcommerceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDi()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalUtil.onAttach(base))
    }

    private fun initDi() {
        startKoin {
            androidContext(this@EcommerceApp)
            modules(applicationModules)
        }
    }
}