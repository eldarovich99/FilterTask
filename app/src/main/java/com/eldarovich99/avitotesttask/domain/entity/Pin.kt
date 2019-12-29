package com.eldarovich99.avitotesttask.domain.entity

import com.yandex.mapkit.geometry.Point

class Pin(val id: Int, val service: String, val coordinates : Coordinate){
    fun toPoint(): Point{
        return Point(this.coordinates.lat, this.coordinates.lng)
    }
}