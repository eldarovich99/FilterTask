package com.eldarovich99.avitotesttask.presentation.map

import com.eldarovich99.avitotesttask.data.ErrorEntity
import com.eldarovich99.avitotesttask.domain.entity.Pin

interface MapActivityView {
    fun showPins(pins: List<Pin>)
    fun refreshPins(pins: List<Pin>)
    fun handleErrors(errorEntity: ErrorEntity)
}