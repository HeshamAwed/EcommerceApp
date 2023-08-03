package com.hesham.ecommerceapp.domain.repositories

import com.hesham.ecommerceapp.domain.entities.DtoLogin
import com.hesham.ecommerceapp.domain.entities.User
import com.hesham.ecommerceapp.domain.entities.UserToken
import com.hesham.ecommerceapp.domain.gateways.local.LocalGateway
import com.hesham.ecommerceapp.domain.gateways.remote.EcommerceGateway
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface AuthRepository {
    suspend fun login(dtoLogin: DtoLogin): UserToken
    suspend fun signUp(dtoUser: User): User
    fun getToken(): String
    fun setToken(token: String)
}

class AuthRepositoryImplementation(
    private val ecommerceGateway: EcommerceGateway,
    private val localGateway: LocalGateway
) : AuthRepository {

    override suspend fun login(dtoLogin: DtoLogin) = ecommerceGateway.login(dtoLogin)
    override suspend fun signUp(dtoUser: User) = ecommerceGateway.signup(dtoUser)

    override fun getToken() = localGateway.getToken()

    override fun setToken(token: String) = localGateway.setToken(token)

}