package com.viewpager.infinite_viewpager2.adapter

import androidx.recyclerview.widget.DiffUtil
import com.viewpager.infinite_viewpager2.model.RecyclerViewItem
import com.viewpager.infinite_viewpager2.model.RecyclerViewItemComparator
import java.lang.ClassCastException

class DiffItemCallback : DiffUtil.ItemCallback<RecyclerViewItem>() {


    override fun areItemsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
        return oldItem.data.toRecyclerViewItemComparator().isItemTheSame(newItem.data.toRecyclerViewItemComparator())
    }

    override fun areContentsTheSame(oldItem: RecyclerViewItem, newItem: RecyclerViewItem): Boolean {
        return oldItem.data.toRecyclerViewItemComparator().isContentTheSame(newItem.data.toRecyclerViewItemComparator())
    }


    private fun Any.toRecyclerViewItemComparator() : RecyclerViewItemComparator {
        try {
            return this as RecyclerViewItemComparator
        }catch (e: ClassCastException) {
            throw IllegalStateException("class ${this.javaClass} must implement ${RecyclerViewItemComparator::class} to be able for comparison")
        }
    }
}