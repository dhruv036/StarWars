package io.dhruv.starwars.di

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.BindsInstance
import dagger.Component
import io.dhruv.starwars.ui.HomeFragment
import io.dhruv.starwars.MainActivity
import io.dhruv.starwars.ui.BottomSheetFragment
import io.dhruv.starwars.ui.DetailsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class,ViewModelModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(detailsFragment: DetailsFragment)

    fun inject(bottomSheetFragment: BottomSheetFragment)

    fun getMap() : Map<Class<*>,ViewModel>

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }


}