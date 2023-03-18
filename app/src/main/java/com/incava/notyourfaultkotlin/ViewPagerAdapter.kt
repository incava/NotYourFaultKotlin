package com.incava.notyourfaultkotlin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    private var fragments : ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
        return fragments[position]
    }
    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    }

    fun addAllFragment(fragmentList: ArrayList<Fragment>){
        val lastNum = fragmentList.size //기존 사이즈에서부터 넣었다고 알리기위해. 처음 넣을때 일일히 notify하기 힘들어서 넣음.
        fragments.addAll(fragmentList)
        notifyItemRangeInserted(lastNum,fragments.size)
    }

    fun removeFragment(){
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }
}