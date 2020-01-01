package com.eldarovich99.avitotesttask.domain

import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.entity.PinsResponse
import javax.inject.Inject

class PinInteractor @Inject constructor(val repository: Repository) {

    suspend fun getPins(): Result<PinsResponse>{
        return repository.getPins()
    }
}