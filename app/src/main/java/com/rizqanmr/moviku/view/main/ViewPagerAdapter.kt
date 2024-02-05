package com.rizqanmr.moviku.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rizqanmr.moviku.utils.Constant.ARG_OBJECT
import com.rizqanmr.moviku.view.discover.DiscoverFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int  = 10

    override fun createFragment(position: Int): Fragment {
        val fragment = DiscoverFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position+1)
        }
        return fragment
    }
}