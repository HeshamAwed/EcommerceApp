package com.hesham.ecommerceapp.domain.gateways.local

import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.hesham.ecommerceapp.utils.Constants


interface LocalGateway {
    fun getToken(): String
    fun setToken(token: String)
    fun setUserId(userId: Int)
    fun getUserId(): Int
    fun changeLanguage()
    fun getCurrentLanguage(): String
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

    override fun changeLanguage() {
        val lang = getCurrentLanguage()
        if (lang == Constants.LANG_AR) {
            sharedPreferences.edit()
                .putString(Constants.LANGUAGE_KEY, Constants.LANG_EN)
                .apply()
        } else {
            sharedPreferences.edit()
                .putString(Constants.LANGUAGE_KEY, Constants.LANG_AR)
                .apply()
        }
    }

    override fun getCurrentLanguage(): String {
        return sharedPreferences.getString(Constants.LANGUAGE_KEY, Constants.LANG_EN)
            ?: Constants.LANG_EN
    }

    override fun logout() {
        sharedPreferences.edit().clear().apply()
    }


}
