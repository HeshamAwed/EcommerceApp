package com.hesham.ecommerceapp.domain.gateways.remote

import com.hesham.ecommerceapp.domain.entities.DtoLogin
import com.hesham.ecommerceapp.domain.entities.Product
import com.hesham.ecommerceapp.domain.entities.UserToken
import com.hesham.ecommerceapp.domain.entities.User
import com.hesham.ecommerceapp.domain.entities.UserId
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EcommerceGateway {

    @POST("auth/login")
    suspend fun login(
        @Body dtoLogin: DtoLogin
    ): UserToken

    @POST("users")
    suspend fun signup(@Body user: User): UserId

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>
}