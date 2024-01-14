package io.dhruv.starwars

import android.app.Application
import dagger.Component
import io.dhruv.starwars.di.ApplicationComponent
import io.dhruv.starwars.di.DaggerApplicationComponent

class StarWarsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}