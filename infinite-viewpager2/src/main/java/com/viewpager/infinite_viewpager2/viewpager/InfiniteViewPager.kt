package com.viewpager.infinite_viewpager2.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.viewpager.infinite_viewpager2.adapter.FragmentAdapter
import com.viewpager.infinite_viewpager2.adapter.RecyclerViewAdapter
import com.viewpager.infinite_viewpager2.model.RecyclerViewItem
import java.lang.IllegalStateException
import kotlin.reflect.KClass


class InfiniteViewPager private constructor(){


    inner class Builder(
        private val activity: FragmentActivity,
        val viewPager: ViewPager2
        ) {

        private var currentPosition: Int = 2
        private var adapterType: AdapterType? = null
        private var fragmentList: MutableList<Class<out Fragment>>? = null
        private var itemList: MutableList<RecyclerViewItem>? = null


        fun setAdapter(adapterType: AdapterType): Builder {
            this.adapterType = adapterType
            viewPager.apply {
                adapter = when(adapterType) {
                    AdapterType.FRAGMENT_STATE_ADAPTER -> { FragmentAdapter(activity) }
                    AdapterType.RECYCLER_VIEW_ADAPTER -> { RecyclerViewAdapter() }
                }
            }
            return this
        }

        fun withItems(vararg items: RecyclerViewItem) : Builder {
            this.itemList =  mutableListOf(*items)
            return this
        }

        fun withFragments(vararg fragments: Class<out Fragment>) : Builder {
            this.fragmentList = mutableListOf(*fragments)
            return this
        }


        fun build() : ViewPager2 {
            return viewPager.apply {
                when(adapterType) {
                    AdapterType.FRAGMENT_STATE_ADAPTER -> {
                        fragmentList?.let { (adapter as FragmentAdapter).updateList(it) }
                    }
                    AdapterType.RECYCLER_VIEW_ADAPTER -> {
                        itemList?.let { (adapter as RecyclerViewAdapter).submitList(it) }
                    }
                }
                registerOnPageChangeCallback(onPageChangeCallback(adapter?.itemCount))
                setCurrentItem(2, false)
            }
        }


        private fun onPageChangeCallback(listSize : Int?) = object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
                println("position = $currentPosition")
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (listSize == null) throw IllegalStateException("List size is null")
                super.onPageScrollStateChanged(state)
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if (currentPosition == 0) {
                        viewPager.setCurrentItem(listSize - 3, false)
                    }else if (currentPosition == listSize - 2) {
                        viewPager.setCurrentItem(1, false)
                    }
                }else if (state == ViewPager2.SCROLL_STATE_DRAGGING && currentPosition == listSize - 1) {
                    viewPager.setCurrentItem(2, false)
                }
            }
        }
    }
}
