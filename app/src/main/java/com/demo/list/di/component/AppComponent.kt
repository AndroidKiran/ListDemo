package com.mania.movie.di.component

import android.support.multidex.MultiDexApplication
import com.demo.list.ListDemoApplication
import com.demo.list.di.module.AppModule
import com.demo.list.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = [(AndroidSupportInjectionModule::class), (AppModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MultiDexApplication): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    fun inject(listDemoApplication: ListDemoApplication)

}