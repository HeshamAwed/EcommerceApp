package com.hesham.ecommerceapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hesham.ecommerceapp.ui.main.MainActivity
import com.hesham.ecommerceapp.databinding.ActivitySplashBinding
import com.hesham.ecommerceapp.ui.auth.AuthenticateActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModel<SplashViewModel>()
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
    }

    private fun initObservers() {
        viewModel.isLoggedInLiveData.observe(this, ::goToNextScreen)
    }

    private fun goToNextScreen(isLoggedIn: Boolean) {
        finish()
        if (isLoggedIn) {
            startActivity(Intent(this, AuthenticateActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}