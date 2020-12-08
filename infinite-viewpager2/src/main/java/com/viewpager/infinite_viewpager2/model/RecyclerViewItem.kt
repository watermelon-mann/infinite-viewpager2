package com.viewpager.infinite_viewpager2.model

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

data class RecyclerViewItem(
    val data: Any,
    @LayoutRes val layoutId: Int,
    val variableId: Int
) {
    fun bind(binding: ViewDataBinding) {
        binding.setVariable(variableId, data)
    }
}

fun Any.toRecyclerViewItem(@LayoutRes layoutId: Int, variableId: Int) : RecyclerViewItem {
    return RecyclerViewItem(this, layoutId, variableId)
}