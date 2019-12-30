package com.eldarovich99.avitotesttask.presentation.map

import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.PinInteractor
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.domain.entity.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapPresenter @Inject constructor(var view: MapActivityView?, private val interactor: PinInteractor) {
    lateinit var pins: List<Pin>
    fun setData(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = interactor.getPins()
            withContext(Dispatchers.Main){
                if (response is Result.Success){
                    pins = response.data.pins
                    view?.showPins(pins)
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
        view?.setServices(services)
    }

    fun onAttach(view: MapActivityView){
        this.view = view
    }

    fun onDetach(){
        this.view = null
    }

    fun refreshPoints(services: ArrayList<Service>) {
        val filteredPins = mutableListOf<Pin>()
        for (service in services){
            if (service.isSelected)
                filteredPins.addAll(pins.filter { it.service == service.title })
        }
        view?.refreshPins(filteredPins)
    }
}