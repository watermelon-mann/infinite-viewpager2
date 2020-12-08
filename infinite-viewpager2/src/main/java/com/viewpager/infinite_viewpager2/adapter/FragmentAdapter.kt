package com.viewpager.infinite_viewpager2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalStateException
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance


class FragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val items: MutableList<Fragment> = mutableListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }

    fun updateList(list: MutableList<Class<out Fragment>>) {
        items.addAll(list.withFakeItems())
        notifyDataSetChanged()
    }


    private fun MutableList<Class<out Fragment>>.withFakeItems() : MutableList<Fragment> {
        val size = this.size
        if (size < 2) throw IllegalStateException("Size of the list $this must be at least 2")
        val mutableListOfFragments = mutableListOf<Fragment>()
        for (i in 0..size + 2) {
            mutableListOfFragments.add(this[(i + size -2) % size].newInstance())
        }
        return mutableListOfFragments
    }



}