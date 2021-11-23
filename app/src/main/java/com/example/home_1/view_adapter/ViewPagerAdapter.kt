package com.example.home_1.view_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.home_1.fragments.AsosiyFragment
import com.example.home_1.fragments.DunyoFragment
import com.example.home_1.fragments.IjtimoiyFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
            0->{
                AsosiyFragment()
            }
            1->{
                DunyoFragment()
            }
            2->{
                IjtimoiyFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}