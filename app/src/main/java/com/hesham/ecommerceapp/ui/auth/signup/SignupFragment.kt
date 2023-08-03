package com.hesham.ecommerceapp.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.databinding.FragmentSignupBinding
import com.hesham.ecommerceapp.ui.ktx.alerter
import com.hesham.ecommerceapp.ui.ktx.showError
import com.hesham.ecommerceapp.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : Fragment() {
    private val viewModel: SignupViewModel by viewModel()
    lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
        initObservers()
    }

    private fun initObservers() {
        viewModel.signUpResultLiveData.observe(viewLifecycleOwner) {
            goToHomeScreen()
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loadingLayout.progressLayout.isVisible = it
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
        viewModel.validationResultLiveData.observe(viewLifecycleOwner) {
            showErrorValidation(it)
        }
    }

    private fun showErrorValidation(message: String) {
        requireActivity().alerter(
            getString(R.string.text_sorry),
            message
        )
    }

    private fun goToHomeScreen() {
        requireActivity().finish()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

    private fun initActions() {
        binding.buttonSignup.setOnClickListener {
            viewModel.validateUserData(
                requireActivity(),
                binding.textFullName.text.toString(),
                binding.textEmail.text.toString(),
                binding.textPassword.text.toString(),
                binding.textConfirmPassword.text.toString()
            )
        }
        binding.buttonLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}