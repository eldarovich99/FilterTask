package com.eldarovich99.avitotesttask.data

import com.eldarovich99.avitotesttask.domain.entity.PinsResponse
import retrofit2.Response
import retrofit2.http.GET

interface PinsApi {
    @GET("pins.json")
    suspend fun getPins() : Response<PinsResponse>
}