package com.eldarovich99.avitotesttask.data

import com.eldarovich99.avitotesttask.utils.Logger
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection


sealed class Result<T>{
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val data: ErrorEntity) : Result<T>()
}

/**
 * The class represents the class of the HTTP error code/
 */
sealed class ErrorEntity {
    abstract val originalException: Throwable?

    data class Network(override val originalException: Throwable?) : ErrorEntity()

    data class NotFound(override val originalException: Throwable?) : ErrorEntity()

    data class AccessDenied(override val originalException: Throwable?) : ErrorEntity()

    data class ServiceUnavailable(override val originalException: Throwable?) : ErrorEntity()

    data class Unknown(override val originalException: Throwable?) : ErrorEntity()

    data class InternalError(override val originalException: Throwable?) : ErrorEntity()
}

/**
 * The class allows to perform safe network calls with error handling.
 */
class ErrorHandlerImpl {
    inline fun <T> executeSafeCall(getData: () -> Response<T>): Result<T> {
        return try {
            val response = getData.invoke()
            if (response.isSuccessful)
                Result.Success(response.body()!!)
            else {
                Result.Error(this.getError(response.code()))
            }
        }
        catch (e: Exception) {
            Result.Error(this.getError(e))
        }
    }

    /**
     * @param code HTTP error code
     * @return ErrorEntity that represents the class of the HTTP error code
     */
    fun getError(code: Int, throwable: Throwable? = null): ErrorEntity {
        return when (code) {
            HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound(throwable)
            HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied(throwable)
            HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable(throwable)
            HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.InternalError(throwable)
            else -> ErrorEntity.Unknown(throwable)
        }
    }

    /**
     * @param throwable is the object that was thrown as a result of network call
     * @return HTTP error code or network error code
     */
    fun getError(throwable: Throwable?): ErrorEntity {
        return when(throwable) {
            is IOException -> {
                Logger.log(this, throwable.message?:"", "IOException occur")
                ErrorEntity.Network(throwable)
            }
            is HttpException -> {
                Logger.log(this, throwable.code().toString(), "HttpException occur")
                getError(throwable.code(), throwable)
            }
            else -> {
                Logger.log(this, throwable?.message?:"")
                ErrorEntity.Unknown(throwable)
            }
        }
    }
}