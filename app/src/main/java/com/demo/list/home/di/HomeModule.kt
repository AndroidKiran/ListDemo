package com.demo.list.home.di

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.demo.list.di.qualifier.ActivityContext
import com.demo.list.di.scope.PerActivity
import com.demo.list.home.ui.HomeActivity
import com.demo.list.home.ui.ItemsAdapter
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    @PerActivity
    @ActivityContext
    fun provideActivityContext(homeActivity: HomeActivity): Context = homeActivity

    @Provides
    @PerActivity
    fun provideItemAdapter() = ItemsAdapter()

    @Provides
    @PerActivity
    fun provideLinearLayoutManager(@ActivityContext context: Context) = LinearLayoutManager(context)
}
