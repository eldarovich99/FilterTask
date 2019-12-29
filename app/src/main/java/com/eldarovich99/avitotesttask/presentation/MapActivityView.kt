package com.eldarovich99.avitotesttask.presentation

import com.eldarovich99.avitotesttask.domain.entity.Pin

interface MapActivityView {
    //fun showPoints()
    fun setServices(services: List<String>)
    fun showPoints(points: List<Pin>)
    fun handleErrors()
}