package com.eldarovich99.avitotesttask.presentation

import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.PinInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapPresenter(var view: MapView?) {
    val interactor by lazy { PinInteractor() }

    fun setData(services: List<Int> ?= listOf(1,2,3)){
        CoroutineScope(Dispatchers.IO).launch {
            val response = interactor.getPins()
            if (response is Result.Success){
                view?.showPoints(services ?: listOf())
            }
            else{
                view?.handleErrors()
            }
        }
    }

    fun onAttach(view: MapView){
        this.view = view
    }

    fun onDetach(){
        this.view = null
    }
}