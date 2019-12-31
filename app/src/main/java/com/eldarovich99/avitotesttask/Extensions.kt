package com.eldarovich99.avitotesttask

import com.eldarovich99.avitotesttask.domain.entity.Service


fun ArrayList<Service>.isEqual(services: ArrayList<Service>?) : Boolean{
    if (this.size != services?.size) return false
    for (index in 0 until this.size){
        if (this[index] != services[index]) return false
    }
    return true
}