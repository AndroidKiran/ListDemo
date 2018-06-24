package com.demo.list.home.base

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import com.demo.list.ListDemoApplication
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel constructor(application: ListDemoApplication) : AndroidViewModel(application) {

    var isNetworkActive = ObservableBoolean()

    private lateinit var baseCompositeDisposable: CompositeDisposable

    var errorLiveData = MutableLiveData<Boolean>()

    fun onViewCreated() {
        baseCompositeDisposable = CompositeDisposable()
    }

    fun onDestroyView() {
        baseCompositeDisposable.dispose()
    }



    fun getCompositeDisposable(): CompositeDisposable = baseCompositeDisposable

}