package com.doing.diproject.home.fragment

import androidx.fragment.app.Fragment
import com.doing.dicommon.component.DiAbsListFragment

class HomeTabFragment : DiAbsListFragment() {

    private lateinit var categoryId: String

    companion object {

        @JvmStatic
        fun newInstance(categoryId: String): Fragment {
            return HomeTabFragment().apply {
                init(categoryId)
            }
        }
    }



    private fun init(categoryId: String) {
        this.categoryId = categoryId
    }
}