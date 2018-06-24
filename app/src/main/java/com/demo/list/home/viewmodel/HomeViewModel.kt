package com.demo.list.home.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.demo.list.ListDemoApplication
import com.demo.list.helper.*
import com.demo.list.home.base.BaseViewModel
import com.demo.list.home.model.ListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class HomeViewModel @Inject constructor(application: ListDemoApplication, private val schedulerProvider: SchedulerProvider) : BaseViewModel(application) {

    @Inject
    lateinit var gson: Gson

    var reload = MutableLiveData<Boolean>()

    private val storageDir = Environment.getExternalStorageDirectory().path + "/Share/"

    var itemsLiveData: LiveData<Result<List<ListItem>>> = reload.switchMap {
        if (!it) {
            MutableLiveData<Result<List<ListItem>>>()
        } else {
            getItemsFromJson()
        }
    }

    var switchImageDownload = MutableLiveData<ListItem>()

    var pathLiveData: LiveData<Result<String>> = switchImageDownload.switchMap {

        when (it) {
            null -> {
                MutableLiveData<Result<String>>()
            }

            else -> {
                val imageFile = "$storageDir/${it.itemId}$JPEG"
                val file = File(imageFile)
                if (file.exists()) {
                    Single.just(Result(imageFile, null))
                            .toLiveData()
                } else {
                    downLoadAndGetImagePath(it)
                }
            }

        }
    }


    private fun getItemsFromJson() = Single.create<List<ListItem>> {

        try {
            val asset = getApplication<ListDemoApplication>().assets?.open(JSON_PATH)
            val size = asset?.available()

            val buffer = ByteArray(size!!)
            asset.apply {
                read(buffer)
                close()
            }
            val typeToken = object : TypeToken<List<ListItem>>() {

            }.type
            val items = gson.fromJson<List<ListItem>>(String(buffer), typeToken)
            it.onSuccess(items)
        } catch (e: IOException) {
            it.onError(e)
        }
    }.getSingleAsync(schedulerProvider).responseToResult()
            .onErrorResumeNext(Single.just(Result(null, NoSuchElementException())))
            .toLiveData()


    private fun downLoadAndGetImagePath(listItem: ListItem): LiveData<Result<String>> =
            downloadImage(listItem)
                    .flatMap {
                        if (it.isSuccess()) {
                            saveImage(it.value, listItem.itemId)
                        } else {
                            Single.just(Result(null, it.error))
                        }
                    }.toLiveData()


    private fun downloadImage(listItem: ListItem) =
            Single.create<Bitmap> { emitter ->
                GlideApp.with(getApplication<ListDemoApplication>())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .load(listItem.bannerUrl)
                        .into(object : SimpleTarget<Bitmap>(600, 400) {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                emitter.onSuccess(resource)
                            }

                            override fun onLoadFailed(errorDrawable: Drawable?) {
                                emitter.onError(NullPointerException())
                            }
                        })

            }.getSingleAsyncUI(schedulerProvider).responseToResult()


    private fun saveImage(bitmap: Bitmap, fileName: String): Single<Result<String>> =

            Single.create<String> { emitter ->

                var success = true
                val fileDir = File(storageDir)
                if (!fileDir.exists()) {
                    success = fileDir.mkdirs()
                }

                if (success) {
                    val imageFile = "$storageDir/$fileName$JPEG"
                    try {
                        val fileOutStream = FileOutputStream(imageFile)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream)
                        fileOutStream.flush()
                        fileOutStream.close()
                        emitter.onSuccess(imageFile)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        emitter.onError(e)
                    }
                }

            }.getSingleAsync(schedulerProvider).responseToResult()


    companion object {
        private const val JSON_PATH = "json/items.json"
        private const val JPEG = ".jpg"
    }
}