package com.eldarovich99.avitotesttask

import com.eldarovich99.avitotesttask.data.RepositoryImpl
import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.PinInteractor
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoryTest {
    val interactor = PinInteractor(RepositoryImpl())
    @Test
    fun wasDataSuccessfullyRetrieved() {
        runBlocking {
            val response = interactor.getPins()
            assert(response is Result.Success &&
                    response.data.pins.isNotEmpty()
            )
        }
    }
}
