package com.hesham.ecommerceapp.domain.repositories

import com.hesham.ecommerceapp.domain.entities.DtoLogin
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.domain.entities.User
import com.hesham.ecommerceapp.domain.entities.UserToken
import com.hesham.ecommerceapp.domain.gateways.local.LocalGateway
import com.hesham.ecommerceapp.domain.gateways.remote.EcommerceGateway
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ProductRepository {
    suspend fun getAllCategories(): List<String>
    suspend fun getProductsByCategory(category: String): List<Product>
}

class ProductRepositoryImplementation(
    private val ecommerceGateway: EcommerceGateway,
) : ProductRepository {

    override suspend fun getAllCategories() = ecommerceGateway.getAllCategories()

    override suspend fun getProductsByCategory(category: String) =
        ecommerceGateway.getProductsByCategory(category)

}