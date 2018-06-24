package com.demo.list.home.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.demo.list.databinding.ItemListBinding
import com.demo.list.home.base.BaseViewHolder
import com.demo.list.home.model.ListItem
import kotlinx.android.synthetic.main.item_list.view.*

class ItemsAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    var itemList = mutableListOf<ListItem>()

    private var itemNavigator: INavigator? = null

    var navigator: INavigator?
        get() = itemNavigator
        set(navigator) {
            if (itemNavigator == null) {
                itemNavigator = navigator
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemBinding = ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ItemViewHolder(itemBinding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setData(list: List<ListItem>) {
        itemList = list as ArrayList<ListItem>
        this.notifyDataSetChanged()
    }

    inner class ItemViewHolder constructor(private val binding: ItemListBinding) : BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            val item = itemList[position].apply {
                this.position = position
            }
            binding.apply {
                this.listItem = item
                this.executePendingBindings()
                this.root.btn_share.setOnClickListener {
                    itemNavigator?.onShareClicked(item)
                }
            }
        }

    }

}