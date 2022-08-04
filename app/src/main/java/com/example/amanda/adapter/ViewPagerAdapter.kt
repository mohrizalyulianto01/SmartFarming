package com.example.amanda.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.amanda.ActivityFragment
import com.example.amanda.GreenhouseFragment

private const val NUM_TABS = 2

public class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var greenhouseFragment = GreenhouseFragment()

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return greenhouseFragment
            1 -> return ActivityFragment()
        }
        return greenhouseFragment
    }
}