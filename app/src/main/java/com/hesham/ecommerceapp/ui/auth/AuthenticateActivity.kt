package com.hesham.ecommerceapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.application.base.BaseActivity

class AuthenticateActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticate)
    }
}