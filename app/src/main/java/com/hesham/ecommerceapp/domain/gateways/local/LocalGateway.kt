package com.hesham.ecommerceapp.domain.gateways.local

import android.content.SharedPreferences
import com.auth0.android.jwt.JWT


interface LocalGateway {
    fun getToken(): String
    fun setToken(token: String)
    fun logout()

    fun getUserId(): Int

}

private const val TOKEN = "token"

class LocalGatewayImplementation(
    private val sharedPreferences: SharedPreferences,
) : LocalGateway {
    override fun getToken(): String {
        return sharedPreferences.getString(TOKEN, "").orEmpty()
    }

    override fun setToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN, token)
            .apply()
    }

    override fun logout() {
        sharedPreferences.edit().clear().apply()
    }

    override fun getUserId(): Int {
        val token = getToken()
        val jwt = JWT(token)
        return jwt.getClaim("sub").asInt() ?: 0
    }
}
