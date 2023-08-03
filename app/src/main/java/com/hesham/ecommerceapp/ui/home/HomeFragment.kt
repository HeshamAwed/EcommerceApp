package com.hesham.ecommerceapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hesham.ecommerceapp.databinding.FragmentHomeBinding
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.ui.home.adapters.CategoryAdapter
import com.hesham.ecommerceapp.ui.home.adapters.ProductsAdapter
import com.hesham.ecommerceapp.ui.ktx.showError
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var productsAdapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initViews() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->
            productsAdapter.filter.filter(text.toString())
        }
    }

    private fun initObservers() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner, ::showCategories)
        viewModel.productsLiveData.observe(viewLifecycleOwner, ::showProducts)
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.loadingLayout.progressLayout.isVisible = it
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            showError(it)
        }
    }

    private fun showProducts(products: List<Product>) {
        productsAdapter = ProductsAdapter(ArrayList(products)) {
            findNavController().navigate(HomeFragmentDirections.goToProductDetails(it))
        }
        binding.listItems.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.listItems.adapter = productsAdapter
    }

    private fun showCategories(categories: List<String>) {
        categories.firstOrNull()?.let {
            viewModel.getProductsByCategory(it)
        }
        val categoriesAdapter = CategoryAdapter(categories) {
            viewModel.getProductsByCategory(it)
        }
        binding.listCategoriesTabs.adapter = categoriesAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllCategories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}