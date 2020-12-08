package com.viewpager.infinite_viewpager2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.viewpager.infinite_viewpager2.model.RecyclerViewItem
import java.lang.IllegalStateException

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.BindingViewHolder>() {

    private val items: MutableList<RecyclerViewItem?> = MutableList(0) {null}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return BindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), viewType, parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        items[position]?.bind(holder.binding)
        holder.binding.apply {
            executePendingBindings()
            root.setOnClickListener {
                onClickEvent.invoke(it, position)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return items[position]?.layoutId ?: throw IllegalStateException("RecyclerViewItem can't be null")
    }


    fun clear() {
        items.clear()
    }

    fun updateList(list: MutableList<RecyclerViewItem>) {
        items.clear()
        items.addAll(list.withFakeItems())
        notifyDataSetChanged()

    }

    private fun MutableList<RecyclerViewItem>.withFakeItems() : MutableList<RecyclerViewItem> {
        val size = this.size
        if (size < 2) throw IllegalStateException("Size of the list $this must be at least 2")
        val mutableListOfItems = mutableListOf<RecyclerViewItem>()
        for (i in 0..size + 2) {
            mutableListOfItems.add(this[(i + size - 2) % size])
        }
        return mutableListOfItems
    }

    private var onClickEvent : (v: View, position: Int) -> Unit = { _: View, _: Int -> }

    fun setOnClickEvent(event: (v: View, position: Int) -> Unit) {
        onClickEvent = event
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
}