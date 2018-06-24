package com.demo.list.helper

import android.databinding.BindingAdapter
import android.graphics.drawable.ShapeDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.text.Html
import android.text.SpannedString
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.list.R


object BindingUtils {

    @JvmStatic
    @BindingAdapter(value = ["imageUrl"])
    fun bindLoadImage(imageView: AppCompatImageView?, url: String?) =
            url?.takeIf { it.isNotEmpty() }?.apply {
                GlideApp.with(imageView?.context!!)
                        .load(this)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView)
            }

    @JvmStatic
    @BindingAdapter(value = ["bindPosition"])
    fun bindShapeToView(view: View?, position: Int) {
        view?.let { viewToUpdate ->
            val color = ColorGenerator.MATERIAL.let {
                it.getColor(position)
            }
            viewToUpdate.background.overrideColor(color)
        }
    }
}



