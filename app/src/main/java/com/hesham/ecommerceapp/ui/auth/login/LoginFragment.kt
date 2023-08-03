package com.hesham.ecommerceapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.hesham.ecommerceapp.ui.main.MainActivity
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.application.base.BaseFragment
import com.hesham.ecommerceapp.databinding.FragmentLoginBinding
import com.hesham.ecommerceapp.domain.entities.UnauthorizedException
import com.hesham.ecommerceapp.ui.ktx.alerter
import com.hesham.ecommerceapp.ui.ktx.showError
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
    private val viewModel: LoginViewModel by viewModel()

    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
        initObservers()
    }

    private fun initObservers() {
        viewModel.loginResultLiveData.observe(viewLifecycleOwner) {
            goToHomeScreen()
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loadingLayout.progressLayout.isVisible = it
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            if (it is UnauthorizedException) {
                alerter(getString(R.string.text_sorry), getString(R.string.error_login_message))
            } else {
                showError(it)
            }
        }
    }

    private fun goToHomeScreen() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

    private fun initActions() {
        binding.buttonLogin.setOnClickListener {
            viewModel.login(
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
        binding.buttonSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.goToSignupFragment())
        }
    }

}