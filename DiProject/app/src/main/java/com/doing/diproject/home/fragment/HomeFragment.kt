package com.doing.diproject.home.fragment

import android.util.SparseArray
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import com.doing.dicommon.component.DiBaseFragment
import com.doing.diproject.R
import com.doing.diproject.home.model.Category
import com.doing.diui.tab.common.IDiTabLayout
import com.doing.diui.tab.top.DiTabTopItemInfo
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : DiBaseFragment() {

    private var mTabPreSelectIndex = 0

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
    }

    fun onCategoryResponse(list: List<Category>) {
        val context = requireContext()
        val tabInfoList = mutableListOf<DiTabTopItemInfo>()
        list.forEach { item ->
            val defaultColor = ContextCompat.getColor(context, R.color.color_333)
            val tintColor = ContextCompat.getColor(context, R.color.color_dd2)
            tabInfoList += DiTabTopItemInfo(item.categoryName, defaultColor, tintColor)
        }

        tabTopView.inflateInfo(tabInfoList)
        tabTopView.select(tabInfoList[0])
        tabTopView.addOnTabSelectedListener(object : IDiTabLayout.OnTabSelectedListener<DiTabTopItemInfo> {
            override fun onTabSelectedChange(index: Int, currentData: DiTabTopItemInfo) {
                if (viewPager.currentItem != index) {
                    viewPager.setCurrentItem(index, false)
                }
                mTabPreSelectIndex = index
            }
        })

        viewPager.adapter = HomePageAdapter(childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, list)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {

            override fun onPageSelected(position: Int) {
                if (mTabPreSelectIndex != position) {
                    tabTopView.select(tabInfoList[position])
                }
            }
        })
        mTabPreSelectIndex = 0
    }

    private inner class HomePageAdapter(
        manager: FragmentManager,  behavior: Int, private val tabs: List<Category>
    ) : FragmentPagerAdapter(manager, behavior) {

        private val fragments = SparseArray<Fragment>()

        override fun getCount(): Int {
            return tabs.size
        }

        override fun getItem(position: Int): Fragment {
            var fragment = fragments.get(position)
            if (fragment == null) {
                fragment = HomeTabFragment.newInstance(tabs[position].categoryId)
                fragments.put(position, fragment)
            }
            return fragment
        }
    }

}