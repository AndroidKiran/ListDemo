package com.demo.list.home.di

import android.arch.lifecycle.ViewModel
import com.demo.list.di.module.ViewModelKey
import com.demo.list.home.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeProviderModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel


}