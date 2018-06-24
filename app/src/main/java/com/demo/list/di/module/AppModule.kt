package com.demo.list.di.module

import android.content.Context
import com.demo.list.ListDemoApplication
import com.demo.list.di.qualifier.AppContext
import com.demo.list.di.scope.PerApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module(includes = [(ActivityBuilderModule::class)])
class AppModule constructor(val application: ListDemoApplication){

    @Provides
    @PerApplication
    fun provideApplication(): ListDemoApplication = application

    @Provides
    @PerApplication
    @AppContext
    fun provideContext(): Context = application.applicationContext

    @Provides
    @PerApplication
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

}