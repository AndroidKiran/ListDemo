package com.demo.list.binding

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.v7.widget.Toolbar
import android.view.View
import com.demo.list.R


class ToolbarConfiguration : BaseObservable() {

    companion object {
        @JvmStatic
        @BindingAdapter("toolbarConfig")
        fun bindToolbarConfiguration(toolbar: Toolbar?, toolbarConfiguration: ToolbarConfiguration?) {
            toolbarConfiguration?.toolbarNavigationIcon?.let {
                toolbar?.navigationIcon = it
            }
            toolbar?.title = toolbarConfiguration?.toolbarTitle
            toolbar?.setTitleTextColor(toolbarConfiguration?.toolbarTitleColor!!)
            toolbar?.setNavigationOnClickListener(toolbarConfiguration?.toolbarNavigationClickListener)
            if (toolbarConfiguration?.toolbarMenu != 0) {
                toolbar?.inflateMenu(toolbarConfiguration?.toolbarMenu!!)
            }
            toolbar?.setOnMenuItemClickListener(toolbarConfiguration?.toolbarMenuItemClickListener)

            if (toolbarConfiguration?.toolbarColor != 0) {
                toolbar?.setBackgroundColor(toolbarConfiguration?.toolbarColor!!)
            }
        }
    }

    @get:Bindable
    var toolbarTitle: String? = null
        private set (value) {
            field = value
        }

    @get:Bindable
    var toolbarNavigationClickListener: View.OnClickListener? = null
        private set (value) {
            field = value
        }

    @get:Bindable
    var toolbarNavigationIcon: Drawable? = null
        private set (value) {
            field = value
        }

    @get:Bindable
    var toolbarTitleColor: Int = 0
        private set(value) {
            field = value
        }

    @get:Bindable
    var toolbarMenu: Int = 0
        private set(value) {
            field = value
        }

    @get:Bindable
    var toolbarMenuItemClickListener: Toolbar.OnMenuItemClickListener? = null
        private set(value) {
            field = value
        }

    @get:Bindable
    var toolbarColor: Int? = R.attr.colorPrimary
        private set(value) {
            field = value
        }

    fun newState(title: String?): Builder = Builder(title)

    inner class Builder constructor(private val title: String?) {

        private var toolbarTitleColor: Int = R.attr.colorPrimary

        private var menu: Int = 0

        private var toolbarNavigationClickListener: View.OnClickListener? = null

        private var toolbarNavigationIcon: Drawable? = null

        private var toolbarMenuItemClickListener: Toolbar.OnMenuItemClickListener? = null

        private var toolbarColor: Int = R.color.colorPrimary

        fun setTitleColor(toolbarTitleColor: Int): Builder {
            this.toolbarTitleColor = toolbarTitleColor
            return this@Builder
        }

        fun setMenu(menu: Int): Builder {
            this.menu = menu
            return this@Builder
        }

        fun setNavigationBackListener(toolbarNavigationClickListener: View.OnClickListener?): Builder {
            this.toolbarNavigationClickListener = toolbarNavigationClickListener
            return this@Builder
        }

        fun setNavigationIcon(toolbarNavigationIcon: Drawable?): Builder {
            this.toolbarNavigationIcon = toolbarNavigationIcon
            return this@Builder
        }

        fun setMenuClickListener(toolbarMenuItemClickListener: Toolbar.OnMenuItemClickListener?): Builder {
            this.toolbarMenuItemClickListener = toolbarMenuItemClickListener
            return this@Builder
        }

        fun setColor(color: Int): Builder {
            this.toolbarColor = color
            return this@Builder
        }

        fun commit() {
            this@ToolbarConfiguration.setToolbarConfig(title, toolbarTitleColor, menu, toolbarNavigationClickListener,
                    toolbarNavigationIcon, toolbarMenuItemClickListener, toolbarColor)
        }

    }

    fun setToolbarConfig(toolbarTitle: String?, toolbarTitleColor: Int, toolbarMenu: Int, toolbarNavigationClickListener: View.OnClickListener?,
                         toolbarNavigationIcon: Drawable?, toolbarMenuItemClickListener: Toolbar.OnMenuItemClickListener?, toolbarColor: Int = R.color.colorPrimary) {
        this.toolbarTitle = toolbarTitle
        this.toolbarTitleColor = toolbarTitleColor
        this.toolbarNavigationClickListener = toolbarNavigationClickListener
        this.toolbarNavigationIcon = toolbarNavigationIcon
        this.toolbarMenu = toolbarMenu
        this.toolbarMenuItemClickListener = toolbarMenuItemClickListener
        this.toolbarColor = toolbarColor
        notifyChange()
    }
}