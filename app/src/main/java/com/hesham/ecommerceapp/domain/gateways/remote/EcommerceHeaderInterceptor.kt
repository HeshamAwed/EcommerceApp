package com.hesham.ecommerceapp.domain.gateways.remote

import com.hesham.ecommerceapp.domain.gateways.local.LocalGateway
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

private const val AUTHORIZATION = "Authorization"
private const val LANGUAGE = "Language"
private const val BEARER = "Bearer"

class OnMarketHeaderInterceptor(private val localGateway: LocalGateway) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        if (localGateway.getToken().isNotEmpty()) {
            request.addHeader(AUTHORIZATION, "$BEARER ${localGateway.getToken()}")
            request.addHeader(LANGUAGE, Locale.getDefault().language)
        }
        return chain.proceed(request.build())
    }
}
