package com.hesham.ecommerceapp.application.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hesham.ecommerceapp.utils.LocalUtil

open class BaseActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        LocalUtil.changeLanguage(this)
        super.onCreate(savedInstanceState)
        LocalUtil.changeLanguage(this)
    }
}