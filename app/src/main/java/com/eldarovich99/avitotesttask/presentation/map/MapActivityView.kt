package com.eldarovich99.avitotesttask.presentation.map

import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.domain.entity.Service

interface MapActivityView {
    //fun showPoints()
    fun setServiceNames(services: ArrayList<Service>)
    fun showPoints(points: List<Pin>)
    fun handleErrors()
}