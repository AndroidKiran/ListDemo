package com.demo.list.home.model

import android.databinding.BaseObservable
import com.google.gson.annotations.SerializedName
import android.text.Html
import android.text.SpannedString
import android.text.TextUtils
import android.text.Spanned
import com.demo.list.R


data class ListItem constructor(
        @SerializedName("id")
        var itemId:String = "",
        @SerializedName("title")
        var title: String = "",
        @SerializedName("type")
        var type: String = "",
        @SerializedName("price")
        var price: String = "",
        @SerializedName("discount")
        var discount: String = "",
        @SerializedName("stocks")
        var stocks: String = "",
        @SerializedName("ribbon_msg")
        var ribbonMsg: String = "",
        @SerializedName("bottom_title")
        var bottomTitle: String = "",
        @SerializedName("bottom_sub_title")
        var bottomSubTitle: String = "",
        @SerializedName("banner_url")
        var bannerUrl: String = ""
) : BaseObservable() {
    var position = 0
}