package com.demo.list

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.demo.list.di.module.AppModule
import com.mania.movie.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ListDemoApplication : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}