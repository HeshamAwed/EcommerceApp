package com.hesham.ecommerceapp.domain.entities

import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import java.util.*
import javax.net.ssl.HttpsURLConnection


@Keep
data class ErrorBody(
    @SerializedName("error")
    val error: String = "",
    @SerializedName("message")
    val message: Any = Any(),
    @SerializedName("statusCode")
    val statusCode: Int = 0
)

object ForbiddenException : Throwable()
object ConflictException : Throwable()
object UnKnownException : Throwable()
object InternalServerErrorException : Throwable()
object UnauthorizedException : Throwable()
object LimitException : Throwable()
object RecordExpiredException : Throwable()
object NotFoundedException : Throwable()
object CantReachServerException : Throwable()


data class APIErrorException(val errorBody: ErrorBody) : Throwable()




fun handleHttpExceptions(throwable: Throwable): Throwable {
    return when (throwable) {
        is HttpException -> {
            when (throwable.code()) {
                HttpsURLConnection.HTTP_FORBIDDEN -> ForbiddenException
                HttpsURLConnection.HTTP_CONFLICT -> ConflictException
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> InternalServerErrorException
                HttpsURLConnection.HTTP_UNAUTHORIZED -> UnauthorizedException
                HttpsURLConnection.HTTP_ENTITY_TOO_LARGE -> LimitException
                HttpsURLConnection.HTTP_BAD_REQUEST -> getErrorFromResponse(throwable.response())
                HttpsURLConnection.HTTP_NOT_ACCEPTABLE -> RecordExpiredException
                HttpsURLConnection.HTTP_NOT_FOUND -> NotFoundedException

                else -> UnKnownException
            }
        }
        is UnknownHostException -> CantReachServerException
        else -> throwable
    }
}

fun getErrorFromResponse(response: Response<*>?): Throwable {
    return try {
        val errorBody = Gson()
            .fromJson(response?.errorBody()?.string(), ErrorBody::class.java)
        APIErrorException(errorBody)
    } catch (exception: Exception) {
        exception
    }
}

fun <T> Flow<T>.handleErrors(result: (Throwable) -> T): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    } catch (e: Throwable) {
     result(handleHttpExceptions(e))
    }
}
