package com.eldarovich99.avitotesttask.data

import com.eldarovich99.avitotesttask.domain.Repository
import com.eldarovich99.avitotesttask.domain.entity.PinsResponse

class RepositoryImpl : Repository {
    private val errorHandler = ErrorHandlerImpl()
    private val pinApi = NetworkClient.getApi(PinsApi::class.java)

    override suspend fun getPins(): Result<PinsResponse> {
        return errorHandler.executeSafeCall { pinApi.getPins() }
    }
}