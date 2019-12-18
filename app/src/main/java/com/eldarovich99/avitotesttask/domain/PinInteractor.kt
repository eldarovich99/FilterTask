package com.eldarovich99.avitotesttask.domain

import com.eldarovich99.avitotesttask.data.RepositoryImpl
import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.entity.PinsResponse

class PinInteractor {
    val repository: Repository by lazy { RepositoryImpl() }

    suspend fun getPins(): Result<PinsResponse>{
        return repository.getPins()
    }
}