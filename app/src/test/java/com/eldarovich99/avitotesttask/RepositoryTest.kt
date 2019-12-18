package com.eldarovich99.avitotesttask

import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.PinInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

class RepositoryTest {
    val interactor by lazy { PinInteractor() }
    @Test
    fun wasDataSuccessfullyRetrieved() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = interactor.getPins()
            assert(response is Result.Success &&
                    response.data.pins.isNotEmpty()
            )
        }
    }
}
