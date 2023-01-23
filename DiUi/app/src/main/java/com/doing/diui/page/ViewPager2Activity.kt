package com.doing.diui.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.doing.diui.R

class ViewPager2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager2)

        val pager = findViewById<ViewPager2>(R.id.ViewPager2Activity_pager)
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pager.adapter = PagerFragmentAdapter(this)
    }

    private class PagerFragmentAdapter(fragmentActivity: FragmentActivity)
        : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return 10
        }

        override fun createFragment(position: Int): Fragment {
            return PagerItemFragment.newInstance(position)
        }
    }
}