package com.eldarovich99.avitotesttask.presentation.map

import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.domain.entity.Service

interface MapActivityView {
    //fun showPoints()
    fun setServices(services: ArrayList<Service>)
    fun showPins(pins: List<Pin>)
    fun refreshPins(pins: List<Pin>)
    fun handleErrors()
}