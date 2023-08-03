package com.hesham.ecommerceapp.domain.entities

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Keep
data class DtoLogin(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
): Serializable