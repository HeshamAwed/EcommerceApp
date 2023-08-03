package com.hesham.ecommerceapp.domain.gateways.local

import android.content.SharedPreferences
import com.auth0.android.jwt.JWT


interface LocalGateway {
    fun getToken(): String
    fun setToken(token: String)
    fun setUserId(userId: Int)
    fun getUserId(): Int
    fun logout()


}

private const val TOKEN = "token"
private const val USER_ID = "user_id"

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
        val jwt = JWT(token)
        val userId = jwt.getClaim("sub").asInt() ?: 0
        setUserId(userId)
    }

    override fun setUserId(userId: Int) {
        sharedPreferences.edit()
            .putInt(USER_ID, userId)
            .apply()
    }

    override fun getUserId(): Int {
        return sharedPreferences.getInt(USER_ID, 0)
    }

    override fun logout() {
        sharedPreferences.edit().clear().apply()
    }


}
