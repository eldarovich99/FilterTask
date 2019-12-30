package com.eldarovich99.avitotesttask.presentation.map

import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.PinInteractor
import com.eldarovich99.avitotesttask.domain.entity.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapPresenter @Inject constructor(var view: MapActivityView?, private val interactor: PinInteractor) {

    fun setData(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = interactor.getPins()
            withContext(Dispatchers.Main){
                if (response is Result.Success){
                    view?.showPoints(response.data.pins)
                    setServices(response.data.services)
                }
                else{
                    view?.handleErrors()
                }
            }
        }
    }

    private fun setServices(serviceNames: List<String>){
        val services = arrayListOf<Service>()
        for (service in serviceNames){
            services.add(Service(service, true))
        }
        view?.setServiceNames(services)
    }

    fun onAttach(view: MapActivityView){
        this.view = view
    }

    fun onDetach(){
        this.view = null
    }
}