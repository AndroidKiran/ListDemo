package com.demo.list.home.ui

import com.demo.list.home.model.ListItem

interface INavigator {

    fun onShareClicked(listItem: ListItem)
}