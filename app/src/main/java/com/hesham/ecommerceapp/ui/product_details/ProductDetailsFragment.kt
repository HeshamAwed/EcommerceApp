package com.hesham.ecommerceapp.ui.product_details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.databinding.FragmentProductDetailsBinding
import com.hesham.ecommerceapp.databinding.FragmentProfileBinding
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.ui.home.HomeViewModel
import com.hesham.ecommerceapp.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModel()

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initProduct(args.product)
        initObservers()
        initActions()
    }

    private fun initObservers() {
        viewModel.productLiveData.observe(viewLifecycleOwner, ::showProduct)
        viewModel.quantityLiveData.observe(viewLifecycleOwner, ::showTotalAdded)
    }

    @SuppressLint("SetTextI18n")
    private fun showProduct(product: Product) {
        val imagePagerAdapter = ImagePagerAdapter(arrayListOf(product.image))
        binding.imagesViewPager.adapter = imagePagerAdapter
        TabLayoutMediator(binding.imagesDots, binding.imagesViewPager) { tab, pos -> }.attach()
        binding.textName.text = product.title
        binding.textDescription.text = product.description
        binding.textOriginalPrice.text = "${product.price} $"
    }

    @SuppressLint("SetTextI18n")
    private fun showTotalAdded(qty: Int) {
        (requireActivity() as MainActivity).updateBadgeCount()
        binding.textQty.text = qty.toString()
        binding.textItemTotalPrice.text = "${(args.product.price * qty)} $"
    }

    private fun initActions() {
        binding.buttonPlus.setOnClickListener {
            viewModel.addToCart()
        }
        binding.buttonMinus.setOnClickListener {
            viewModel.removeFromCart()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}