package com.eldarovich99.avitotesttask.domain

import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.entity.PinsResponse

interface Repository {
    suspend fun getPins(): Result<PinsResponse>
}