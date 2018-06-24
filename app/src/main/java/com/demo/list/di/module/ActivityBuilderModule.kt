package com.demo.list.di.module

import android.arch.lifecycle.ViewModelProvider
import com.demo.list.ViewModelFactory
import com.demo.list.di.scope.PerActivity
import com.demo.list.home.di.HomeModule
import com.demo.list.home.di.HomeProviderModule
import com.demo.list.home.ui.HomeActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @PerActivity
    @ContributesAndroidInjector(modules = [(HomeProviderModule::class), (HomeModule::class)])
    abstract fun bindHomeActivity(): HomeActivity
}
