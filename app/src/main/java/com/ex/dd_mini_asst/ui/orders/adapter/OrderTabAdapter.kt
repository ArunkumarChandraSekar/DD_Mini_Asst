package com.ex.dd_mini_asst.ui.orders.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ex.dd_mini_asst.ui.orders.MainActivity

import com.ex.dd_mini_asst.ui.orders.frag.IncomingFrag


class OrderTabAdapter(val fa: FragmentActivity): FragmentStateAdapter(fa)  {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                IncomingFrag.newInstance(MainActivity.FragType.INCOMING.type)
            }
            1 -> {
                IncomingFrag.newInstance(MainActivity.FragType.PREPARING.type)
            }
            else -> {
                IncomingFrag.newInstance(MainActivity.FragType.COLLECTION.type)
            }
        }
    }
}