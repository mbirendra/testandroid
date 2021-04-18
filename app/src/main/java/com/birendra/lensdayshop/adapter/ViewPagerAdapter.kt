package com.birendra.lensdayshop.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val fragmentManager: FragmentManager,val lifecycle: Lifecycle,val lstFragment:MutableList<Fragment>):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return lstFragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return lstFragment[position]
    }
}