package com.eldarovich99.avitotesttask

import com.eldarovich99.avitotesttask.data.ErrorEntity
import com.eldarovich99.avitotesttask.data.RepositoryImpl
import com.eldarovich99.avitotesttask.domain.PinInteractor
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.domain.entity.Service
import com.eldarovich99.avitotesttask.presentation.map.MapActivityView
import com.eldarovich99.avitotesttask.presentation.map.MapPresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor

@ExperimentalCoroutinesApi
class PresenterTest {
    val interactor = PinInteractor(RepositoryImpl())

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineScope().coroutineContext[ContinuationInterceptor] as CoroutineDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }
    @Test
    fun setAndRefreshPinsTest() {
        runBlocking {
            var step = 0
            val view = object: MapActivityView{
                override fun showPins(pins: List<Pin>) {
                    assert(pins.size == 167)
                }

                override fun refreshPins(pins: List<Pin>) {
                    when (step){
                        0 -> assert(pins.size == 56)
                        1 -> assert(pins.size == 56)
                        2 -> assert(pins.size == 54)
                        3 -> assert(pins.size == 112)
                        4 -> assert(pins.size == 110)
                        5 -> assert(pins.size == 110)
                        6 -> assert(pins.size == 166)
                    }
                    step++
                }

                override fun handleErrors(errorEntity: ErrorEntity) {
                    assert(false)
                }
            }
            val presenter = MapPresenter(view, interactor)
            presenter.setData(true)
            presenter.refreshServicesAndPins(arrayListOf(Service("a", true), Service("b", false), Service("c", false)))
            presenter.refreshServicesAndPins(arrayListOf(Service("a", false), Service("b", true), Service("c", false)))
            presenter.refreshServicesAndPins(arrayListOf(Service("a", false), Service("b", false), Service("c", true)))
            presenter.refreshServicesAndPins(arrayListOf(Service("a", true), Service("b", true), Service("c", false)))
            presenter.refreshServicesAndPins(arrayListOf(Service("a", true), Service("b", false), Service("c", true)))
            presenter.refreshServicesAndPins(arrayListOf(Service("a", false), Service("b", true), Service("c", true)))
            presenter.refreshServicesAndPins(arrayListOf(Service("a", true), Service("b", true), Service("c", true)))
        }
    }
}