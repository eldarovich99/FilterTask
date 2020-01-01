package com.eldarovich99.avitotesttask.presentation.map

import android.content.Intent
import com.eldarovich99.avitotesttask.data.Result
import com.eldarovich99.avitotesttask.domain.PinInteractor
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.domain.entity.Service
import com.eldarovich99.avitotesttask.isEqual
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapPresenter @Inject constructor(var view: MapActivityView?, private val interactor: PinInteractor) {
    lateinit var pins: List<Pin>
    private var services : ArrayList<Service>?=null

    /**
     * Loads data from network if it is initial loading or sets pins if this method is called after activity recreation
     * @param isInitialLoading true if network call is neccessary, false if activity was recreated and pins should be just placed on the map
     */
    fun setData(isInitialLoading: Boolean){
        if (isInitialLoading) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = interactor.getPins()
                withContext(Dispatchers.Main) {
                    if (response is Result.Success) {
                        pins = response.data.pins
                        view?.showPins(pins)
                        setServices(response.data.services)
                    } else {
                        view?.handleErrors((response as Result.Error).data)
                    }
                }
            }
        }
        else
            refreshPins()
    }

    fun attachServicesToIntent(intent: Intent){
        intent.putParcelableArrayListExtra(MapActivity.SERVICES, services)
    }

    private fun setServices(serviceNames: List<String>){
        services = arrayListOf()
        for (service in serviceNames){
            services!!.add(Service(service, true))
        }
    }

    fun onAttach(view: MapActivityView){
        this.view = view
    }

    fun onDetach(){
        this.view = null
    }

    /**
     * Refreshes pins according to selected services
     */
    private fun refreshPins() {
        val filteredPins = mutableListOf<Pin>()
        for (service in services!!) {
            if (service.isSelected)
                filteredPins.addAll(pins.filter { it.service == service.title })
        }
        view?.refreshPins(filteredPins)
    }

    /**
     * Checks necessity to refresh pins and then refreshes pins according to selected services
     */
    fun refreshServicesAndPins(newServices: ArrayList<Service>) {
        if (!newServices.isEqual(services)) {
            services = newServices
            refreshPins()
        }
    }
}