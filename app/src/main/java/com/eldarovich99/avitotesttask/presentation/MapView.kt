package com.eldarovich99.avitotesttask.presentation

interface MapView {
    fun showPoints()
    fun showPoints(type: List<Int>)
    fun handleErrors()
}