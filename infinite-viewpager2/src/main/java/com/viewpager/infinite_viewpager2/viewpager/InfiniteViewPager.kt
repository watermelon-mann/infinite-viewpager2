package com.viewpager.infinite_viewpager2.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.viewpager.infinite_viewpager2.adapter.FragmentAdapter
import java.lang.IllegalStateException
import kotlin.reflect.KClass


class InfiniteViewPager private constructor(){


    class Builder<T>(private val activity: FragmentActivity) {

        private var currentPosition: Int = 2
        private var viewPager: ViewPager2? = null
        private var adapterType: AdapterType? = null
        private var fragmentAdapter: FragmentAdapter? = null
        //private var adapter: Adapter? = null
        private var fragmentList: MutableList<KClass<out Fragment>>? = null
        private var itemList: MutableList<T>? = null


        fun withViewPager(viewPager: ViewPager2): Builder<T> {
            this.viewPager = viewPager
            return this
        }

        fun setAdapter(adapterType: AdapterType): Builder<T> {
            this.adapterType = adapterType
            when(adapterType) {
                AdapterType.FRAGMENT_STATE_ADAPTER -> {
                    viewPager?.let {
                        fragmentAdapter = FragmentAdapter(activity)
                        it.adapter = fragmentAdapter
                    } ?: throw IllegalStateException("View Pager must be initialized before adapter")
                }
                AdapterType.RECYCLER_VIEW_ADAPTER -> {
//                    viewPager?.let {
//                        this.adapter = Adapter()
//                        it.adapter = this.adapter
//                    } ?: throw IllegalStateException("View Pager must be initialized before adapter")
                }
            }
            return this
        }

        fun withItems(vararg items: T) : Builder<T> {
            this.itemList =  mutableListOf(*items)
            return this
        }

        fun withFragments(vararg fragments: KClass<out Fragment>) : Builder<T> {
            this.fragmentList = mutableListOf(*fragments)
            return this
        }

        fun build() : ViewPager2 {
            return viewPager?.apply {
                when(adapterType) {
                    AdapterType.FRAGMENT_STATE_ADAPTER -> {
                        fragmentList?.let { fragmentAdapter?.updateList(it) }
                    }
                    AdapterType.RECYCLER_VIEW_ADAPTER -> {
                        //itemList?.let { this@Builder.adapter?.updateList(it) }
                    }
                }
                registerOnPageChangeCallback(onPageChangeCallback(this.adapter?.itemCount))
                this.setCurrentItem(2, false)
            } ?: throw IllegalStateException("View Pager can't be null")
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
                        viewPager?.setCurrentItem(listSize - 3, false)
                    }else if (currentPosition == listSize - 2) {
                        viewPager?.setCurrentItem(1, false)
                    }
                }else if (state == ViewPager2.SCROLL_STATE_DRAGGING && currentPosition == listSize - 1) {
                    viewPager?.setCurrentItem(2, false)
                }
            }
        }
    }
}
