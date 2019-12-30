package com.eldarovich99.avitotesttask

import android.app.Application
import com.eldarovich99.avitotesttask.di.scopes.ApplicationScope
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module

class App : Application(){
    lateinit var scope: Scope
    override fun onCreate() {
        super.onCreate()
        scope = KTP.openScope(ApplicationScope::class.java)
            .installModules(module {
                bind<Application>().toInstance { this@App }
            })
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        scope.release()
    }
}