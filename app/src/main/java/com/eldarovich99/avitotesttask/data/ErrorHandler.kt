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

sealed class ErrorEntity {
    abstract val originalException: Throwable?

    data class Network(override val originalException: Throwable?) : ErrorEntity()

    data class NotFound(override val originalException: Throwable?) : ErrorEntity()

    data class AccessDenied(override val originalException: Throwable?) : ErrorEntity()

    data class ServiceUnavailable(override val originalException: Throwable?) : ErrorEntity()

    data class Unknown(override val originalException: Throwable?) : ErrorEntity()

    data class InternalError(override val originalException: Throwable?) : ErrorEntity()
}

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

    fun getError(code: Int, throwable: Throwable? = null): ErrorEntity {
        return when (code) {
// no cache found in case of no network, thrown by retrofit -> treated as network error
//DataConstants.Network.HttpStatusCode.UNSATISFIABLE_REQUEST -> ErrorEntity.Network(throwable)

// not found
            HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound(throwable)

// access denied
            HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied(throwable)

// unavailable service
            HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable(throwable)

// server error
            HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.InternalError(throwable)
// all the others will be treated as unknown error
            else -> ErrorEntity.Unknown(throwable)
        }
    }

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