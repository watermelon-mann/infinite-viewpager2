package com.viewpager.infinite_viewpager2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.viewpager.infinite_viewpager2.model.RecyclerViewItem

class RecyclerViewAdapter : ListAdapter<RecyclerViewItem, RecyclerViewAdapter.BindingViewHolder>(DiffItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return BindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), viewType, parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).bind(holder.binding)
        holder.binding.apply {
            executePendingBindings()
            root.setOnClickListener {
                onClickEvent.invoke(it, position)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }


    fun clear() {
        currentList.toMutableList().clear()
    }


    private var onClickEvent : (v: View, position: Int) -> Unit = { _: View, _: Int -> }

    fun setOnClickEvent(event: (v: View, position: Int) -> Unit) {
        onClickEvent = event
    }


    inner class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}