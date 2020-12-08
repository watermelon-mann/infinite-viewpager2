package com.viewpager.infinite_viewpager2.model

interface RecyclerViewItemComparator {
    fun isItemTheSame(other: Any) : Boolean
    fun isContentTheSame(other: Any) : Boolean
}