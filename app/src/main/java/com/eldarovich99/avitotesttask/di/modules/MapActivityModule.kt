package com.eldarovich99.avitotesttask.di.modules

import com.eldarovich99.avitotesttask.data.RepositoryImpl
import com.eldarovich99.avitotesttask.domain.PinInteractor
import com.eldarovich99.avitotesttask.domain.Repository
import com.eldarovich99.avitotesttask.presentation.map.MapActivityView
import com.eldarovich99.avitotesttask.presentation.map.MapPresenter
import toothpick.config.Module

class MapActivityModule(view: MapActivityView) : Module(){
    init {
        val repository = RepositoryImpl()
        bind(Repository::class.java).toInstance(repository)
        val interactor = PinInteractor(repository)
        bind(PinInteractor::class.java).toInstance(interactor)
        bind(MapPresenter::class.java).toInstance(MapPresenter(view, interactor))
    }
}