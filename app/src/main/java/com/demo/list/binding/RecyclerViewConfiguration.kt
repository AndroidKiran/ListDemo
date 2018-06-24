package com.demo.list.binding

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class RecyclerViewConfiguration : BaseObservable() {

    companion object {
        @JvmStatic
        @BindingAdapter("recyclerBinding")
        fun bindRecyclerViewConfiguration(recyclerView: RecyclerView?, recyclerViewConfig: RecyclerViewConfiguration?) {
            recyclerView?.setHasFixedSize(true)
            recyclerViewConfig?.let {
                it.layoutManager?.apply {
                    it.stackFromEnd?.let {
                        this.stackFromEnd = it
                    }

                    it.reverseLayout?.let {
                        this.reverseLayout = it
                    }
                }?.also {
                    recyclerView?.layoutManager = it
                }
            }

            recyclerView?.adapter = recyclerViewConfig?.recyclerAdapter
        }
    }

    @get:Bindable
    var layoutManager: LinearLayoutManager? = null
        private set(value) {
            field = value
        }

    @get:Bindable
    var recyclerAdapter: RecyclerView.Adapter<*>? = null
        private set(value) {
            field = value
        }

    @get:Bindable
    var stackFromEnd: Boolean? = false
        private set(value) {
            field = value
        }

    @get:Bindable
    var reverseLayout: Boolean? = false
        private set(value) {
            field = value
        }

    fun newState(recyclerAdapter: RecyclerView.Adapter<*>?): Builder = Builder(recyclerAdapter)

    inner class Builder constructor(private val recyclerAdapter: RecyclerView.Adapter<*>?) {

        private var layoutManager: LinearLayoutManager? = null

        private var stackFromEnd: Boolean? = false

        private var isReverseLayout: Boolean? = false

        fun setLayoutManger(layoutManager: LinearLayoutManager?): Builder {
            this.layoutManager = layoutManager
            return this@Builder
        }

        fun setStackFromEnd(stackFromEnd: Boolean): Builder {
            this.stackFromEnd = stackFromEnd
            return this@Builder
        }

        fun setReverseLayout(isReverseLayout: Boolean): Builder {
            this.isReverseLayout = isReverseLayout
            return this@Builder
        }

        fun commit() {
            this@RecyclerViewConfiguration.setRecyclerConfig(recyclerAdapter, layoutManager, stackFromEnd, isReverseLayout)
        }

    }

    fun setRecyclerConfig(recyclerAdapter: RecyclerView.Adapter<*>?, layoutManager: LinearLayoutManager?,
                          stackFromEnd: Boolean? = false, isReverseLayout: Boolean? = false) {
        this.layoutManager = layoutManager
        this.recyclerAdapter = recyclerAdapter
        this.stackFromEnd = stackFromEnd
        this.reverseLayout = isReverseLayout
        notifyChange()
    }

}

