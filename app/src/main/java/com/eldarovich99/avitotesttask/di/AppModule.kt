package com.eldarovich99.avitotesttask.di

import android.app.Application
import toothpick.config.Module

class AppModule(private val applicationContext : Application) : Module(){
    init {
        bind(Application::class.java).toInstance(applicationContext)
    }
}