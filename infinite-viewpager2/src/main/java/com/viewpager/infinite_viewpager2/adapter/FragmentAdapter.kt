package com.viewpager.infinite_viewpager2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
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

    fun updateList(list: MutableList<KClass<out Fragment>>) {
        items.addAll(list.withFakeItems())
        println("list = $items")
        notifyDataSetChanged()
    }


    private fun MutableList<KClass<out Fragment>>.withFakeItems() : MutableList<Fragment> {

        val size = this.size
        val mutableListOfFragments = mutableListOf<Fragment>()
        for (i in 0..size + 2) {
            mutableListOfFragments.add(this[(i + size -2) % size].createInstance())
        }
        return mutableListOfFragments
    }



}