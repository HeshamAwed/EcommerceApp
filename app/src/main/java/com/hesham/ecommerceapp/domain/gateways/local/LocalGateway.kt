package com.hesham.ecommerceapp.domain.gateways.local

import android.content.SharedPreferences

interface LocalGateway {
    fun getToken(): String
    fun setToken(token: String)

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
}
