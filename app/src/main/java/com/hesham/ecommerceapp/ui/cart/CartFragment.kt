package com.hesham.ecommerceapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hesham.ecommerceapp.R
import com.hesham.ecommerceapp.databinding.FragmentCartBinding
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.ui.main.MainActivity
import com.hesham.ecommerceapp.ui.product_details.ProductDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    private val viewModel: CartViewModel by viewModel()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.productsLiveData.observe(viewLifecycleOwner, ::showCart)
        viewModel.quantityLiveData.observe(viewLifecycleOwner, ::showQuantity)
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::showLoading)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingLayout.progressLayout.isVisible = isLoading
    }

    private fun showQuantity(quantity: Int) {
        (requireActivity() as MainActivity).updateBadgeCount()
        viewModel.getCart()
    }

    private fun showCart(products: List<Product>) {
        val totalPrice = products.sumOf { (it.quantity * it.price) }
        binding.textTotalPrice.text = getString(R.string.total_price, "$totalPrice $")

        val cartAdapter = CartAdapter(products, onItemClicked = {
            findNavController().navigate(CartFragmentDirections.goToProductDetails(it))
        }, onAddToCartClicked = {
            viewModel.addToCart(it)
        }, onRemoveFromCartClicked = {
            viewModel.removeFromCart(it)
        })

        binding.listCart.adapter = cartAdapter
        binding.layoutCart.isVisible = products.isNotEmpty()
        binding.layoutPlaceHolder.isVisible = products.isEmpty()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}