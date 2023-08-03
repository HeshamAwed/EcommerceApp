package com.hesham.ecommerceapp.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.databinding.FragmentLoginBinding
import com.hesham.ecommerceapp.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
    }

    private fun initActions() {
        binding.buttonSignup.setOnClickListener {

        }
        binding.buttonLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}