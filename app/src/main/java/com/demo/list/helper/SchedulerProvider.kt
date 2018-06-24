package com.demo.list.helper

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProvider @Inject constructor() {

    fun io(): Scheduler = Schedulers.io()

    fun ui(): Scheduler = AndroidSchedulers.mainThread()

}
