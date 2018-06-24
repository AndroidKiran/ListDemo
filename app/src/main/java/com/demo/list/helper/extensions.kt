package com.demo.list.helper

import android.arch.lifecycle.*
import android.graphics.drawable.*
import android.support.annotation.ColorInt
import com.demo.list.binding.SnackbarConfiguration
import io.reactivex.Single

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) = observe(owner, Observer<T> { v -> observer.invoke(v) })

fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>) = Transformations.switchMap(this, func)

fun <T> Single<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this.toFlowable())

fun <T> Single<T>.getSingleAsync(schedulerProvider: SchedulerProvider): Single<T> =
        this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())

fun <T> Single<T>.getSingleAsyncUI(schedulerProvider: SchedulerProvider): Single<T> =
        this.subscribeOn(schedulerProvider.ui()).observeOn(schedulerProvider.ui())

fun <T> Single<T>.responseToResult(): Single<Result<T>> =
        this.map { it.asResult() }
                .onErrorReturn { return@onErrorReturn it.asErrorResult<T>() }

fun <T> T.asResult(): Result<T> = Result(this, null)

fun <T> Throwable.asErrorResult(): Result<T> = Result(null, this)

fun Drawable.overrideColor(@ColorInt colorInt: Int) {
    when (this) {
        is GradientDrawable -> setColor(colorInt)
        is ShapeDrawable -> paint.color = colorInt
        is ColorDrawable -> color = colorInt
    }
}

fun SnackbarConfiguration.showSnackBar(msg: String, type: SnackbarConfiguration.Type, duration: Int) =
        this.newState(msg)
                .setDuration(duration)
                .setType(type)
                .commit()


