package com.hesham.ecommerceapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.geniusforapp.fancydialog.builders.FancyDialogBuilder
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.databinding.FragmentProfileBinding
import com.hesham.ecommerceapp.domain.entities.User
import com.hesham.ecommerceapp.ui.home.HomeViewModel
import com.hesham.ecommerceapp.ui.ktx.alerter
import com.hesham.ecommerceapp.ui.ktx.showError
import com.hesham.ecommerceapp.ui.splash.SplashActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActions()
        initObservers()
    }

    private fun initObservers() {
        viewModel.logoutResultLiveData.observe(viewLifecycleOwner, ::userLoggedOut)
        viewModel.userResultLiveData.observe(viewLifecycleOwner, ::showUserData)
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loadingLayout.progressLayout.isVisible = it
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
    }

    private fun showUserData(user: User) {
        binding.textName.text = user.getFullName()
        binding.textEmail.text = user.email.orEmpty()
    }

    private fun userLoggedOut(b: Boolean?) {
        startActivity(
            Intent(requireContext(), SplashActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }

    private fun initActions() {
        binding.buttonLogout.setOnClickListener {
            FancyDialogBuilder(requireContext(), R.style.AppFancyDialogStyle)
                .withImageIcon(R.drawable.ic_alert_discount)
                .withTitle(getString(R.string.log_out))
                .withSubTitle(getString(R.string.do_you_want_to_logout))
                .withTitleTypeFace(R.font.bold)
                .withSubTitleTypeFace(R.font.reguler)
                .withPositive(android.R.string.ok) { w, d ->
                    viewModel.logout()
                }
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}