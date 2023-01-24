package com.doing.diui.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.findNavController
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

        findViewById<Button>(R.id.ViewPager2Activity_btn_next).setOnClickListener {
            Toast.makeText(this, "Navigation跳转测试", Toast.LENGTH_SHORT).show()
//            val controller = findNavController(R.id.nav_host_fragment_activity_navigation)
//            controller.navigate(R.id.navigation_activity_viewpager2)
        }
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