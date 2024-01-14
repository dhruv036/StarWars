package io.dhruv.starwars.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import io.dhruv.starwars.viewModel.CharacterViewModel
import io.dhruv.starwars.viewModel.DetailFragmentViewModel
import io.dhruv.starwars.viewModel.HomeFragmentViewModel

@Module
abstract class ViewModelModule {


    @Binds
    @ClassKey(CharacterViewModel::class)
    @IntoMap
    abstract fun characterViewModel(characterViewModel: CharacterViewModel) : ViewModel

    @Binds
    @ClassKey(HomeFragmentViewModel::class)
    @IntoMap
    abstract fun homeFragmentViewModel(characterViewModel: HomeFragmentViewModel) : ViewModel

    @Binds
    @ClassKey(DetailFragmentViewModel::class)
    @IntoMap
    abstract fun DetailFragmentViewModel(detailFragmentViewModel: DetailFragmentViewModel) : ViewModel
}