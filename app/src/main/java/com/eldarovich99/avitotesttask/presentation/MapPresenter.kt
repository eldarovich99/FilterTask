package com.eldarovich99.avitotesttask.presentation

import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.PinInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapPresenter(var view: MapActivityView?) {
    val interactor by lazy { PinInteractor() }

    fun setData(services: List<Int> ?= listOf(1,2,3)){
        CoroutineScope(Dispatchers.IO).launch {
            val response = interactor.getPins()
            withContext(Dispatchers.Main){
                if (response is Result.Success){
                    view?.showPoints(response.data.pins)
                }
                else{
                    view?.handleErrors()
                }
            }
        }
    }

    fun onAttach(view: MapActivityView){
        this.view = view
    }

    fun onDetach(){
        this.view = null
    }
}