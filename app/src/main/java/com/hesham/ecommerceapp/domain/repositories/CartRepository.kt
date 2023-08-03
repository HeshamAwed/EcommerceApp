package com.hesham.ecommerceapp.domain.repositories

import com.hesham.ecommerceapp.domain.entities.DtoLogin
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.domain.entities.User
import com.hesham.ecommerceapp.domain.entities.UserToken
import com.hesham.ecommerceapp.domain.gateways.local.LocalGateway
import com.hesham.ecommerceapp.domain.gateways.local.dao.ProductDao
import com.hesham.ecommerceapp.domain.gateways.remote.EcommerceGateway
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CartRepository {
    suspend fun addToCart(product: Product): Int
    suspend fun removeFromCart(product: Product): Int
    fun getProductQuantityFromCart(productId: Int): Int
    fun getCart(): List<Product>
    fun getTotalProductAdded():Int
}

class CartRepositoryImplementation(
    private val productDao: ProductDao
) : CartRepository {
    override suspend fun addToCart(product: Product): Int {
        var totalAdd = productDao.getProduct(product.id)?.quantity ?: 0
        totalAdd++
        productDao.insert(product.copy(quantity = totalAdd))
        return totalAdd
    }

    override suspend fun removeFromCart(product: Product): Int {
        var totalAdd = productDao.getProduct(product.id)?.quantity ?: 0
        if (totalAdd > 1) {
            totalAdd--
            productDao.insert(product.copy(quantity = totalAdd))
        } else {
            totalAdd = 0
            productDao.delete(product)
        }
        return totalAdd
    }

    override fun getProductQuantityFromCart(productId: Int): Int {
        return productDao.getProduct(productId)?.quantity ?: 0
    }

    override fun getCart(): List<Product> {
        return productDao.getAllProduct()
    }
    override fun getTotalProductAdded(): Int {
        return productDao.getAllProduct().count()
    }

}