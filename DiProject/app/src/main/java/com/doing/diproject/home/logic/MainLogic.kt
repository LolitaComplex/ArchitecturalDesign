package com.doing.diproject.home.logic

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import com.doing.diproject.R
import com.doing.dicommon.ActivityProvider
import com.doing.diproject.home.fragment.*
import com.doing.diproject.home.tab.DiTabFragmentAdapter
import com.doing.diproject.home.tab.DiTabFragmentView
import com.doing.diui.tab.bottom.DiTabBottomInfo
import com.doing.diui.tab.bottom.DiTabBottomView
import com.doing.diui.tab.common.IDiTabLayout
import com.doing.hilibrary.util.DiDisplayUtil

class MainLogic(private val provider: ActivityProvider) {

    private var mCurrentPosition = 0
    private lateinit var mTabFragmentView: DiTabFragmentView
    private lateinit var mTabBottomLayout: DiTabBottomView
    private lateinit var mDataList: MutableList<DiTabBottomInfo>

    companion object {
        const val TAG_CURRENT_POSITION = "tag_current_position_main_activity"
    }


    fun initLogic(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(TAG_CURRENT_POSITION)
        }

        val clazz = HomeFragment::class.java

        val tabFragmentView = provider.findViewById<DiTabFragmentView>(R.id.MainActivity_tab_view)
        val dataList = mutableListOf(
            DiTabBottomInfo(
                HomeFragment::class.java,
                "首页",
                "fonts/iconfont.ttf",
                provider.getString(R.string.if_home),
                null,
                Color.parseColor("#FF656667"),
                Color.parseColor("#FFD44949")
            ), DiTabBottomInfo(
                ChatFragment::class.java,
                "私信",
                "fonts/iconfont.ttf",
                provider.getString(R.string.if_chat),
                null,
                Color.parseColor("#FF656667"),
                Color.parseColor("#FFD44949")
            ), DiTabBottomInfo(
                CategoryFragment::class.java,
                "分类",
                BitmapFactory.decodeResource(provider.getResources(), R.drawable.fire),
                BitmapFactory.decodeResource(provider.getResources(), R.drawable.fire),
            ), DiTabBottomInfo(
                FavoriteFragment::class.java,
                "收藏",
                "fonts/iconfont.ttf",
                provider.getString(R.string.if_favorite),
                null,
                Color.parseColor("#FF656667"),
                Color.parseColor("#FFD44949")
            ), DiTabBottomInfo(
                ProfileFragment::class.java,
                "我的",
                "fonts/iconfont.ttf",
                provider.getString(R.string.if_profile),
                null,
                Color.parseColor("#FF656667"),
                Color.parseColor("#FFD44949")
            )
        )
        val adapter = DiTabFragmentAdapter(provider.getSupportFragmentManager(), dataList)
        tabFragmentView.setAdapter(adapter)

        val tabBottom = provider.findViewById<DiTabBottomView>(R.id.MainActivity_tab_bottom)
        tabBottom.inflateInfo(dataList)
        tabBottom.findTab(dataList[2])?.resetTab(DiDisplayUtil.dp2px(66.0f))
        tabBottom.addOnTabSelectedListener(object : IDiTabLayout.OnTabSelectedListener<DiTabBottomInfo> {
            override fun onTabSelectedChange(index: Int, currentData: DiTabBottomInfo) {
                tabFragmentView.setCurrentItem(index)
                mCurrentPosition = index
            }
        })
        tabBottom.select(dataList[mCurrentPosition])

        mTabFragmentView = tabFragmentView
        mTabBottomLayout = tabBottom
        mDataList = dataList
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(TAG_CURRENT_POSITION, mCurrentPosition)
    }

}