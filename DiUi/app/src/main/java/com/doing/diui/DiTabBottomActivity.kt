package com.doing.diui

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doing.diui.tab.bottom.DiTabBottomInfo
import com.doing.diui.tab.bottom.DiTabBottomView
import com.doing.hilibrary.util.DiDisplayUtil

class DiTabBottomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_di_tab_bottom)

        //        val tabItem = findViewById<DiTabBottom>(R.id.MainActivity_tab_item)
//        tabItem.setTabInfo(
//            DiTabBottomInfo("首页",
//                BitmapFactory.decodeResource(resources, R.drawable.img),
//                BitmapFactory.decodeResource(resources, R.drawable.img)), 0)
//        tabItem.setTabInfo(
//            DiTabBottomInfo(
//                "首页",
//                "fonts/iconfont.ttf",
//                getString(R.string.if_home),
//                getString(R.string.if_home),
//                Color.parseColor("#FF656667"),
//                Color.parseColor("#FFD44949")
//            ), 0)

        val tabBottom = findViewById<DiTabBottomView>(R.id.MainActivity_tab)
        val dataList = mutableListOf(
            DiTabBottomInfo(
                DemoFragment::class.java,
                "首页",
                "fonts/iconfont.ttf",
                getString(R.string.if_home),
                null,
                Color.parseColor("#FF656667"),
                Color.parseColor("#FFD44949")
            ), DiTabBottomInfo(
                DemoFragment::class.java,
                "私信",
                "fonts/iconfont.ttf",
                getString(R.string.if_chat),
                null,
                Color.parseColor("#FF656667"),
                Color.parseColor("#FFD44949")
            ), DiTabBottomInfo(
                DemoFragment::class.java,
                "分类",
                BitmapFactory.decodeResource(resources, R.drawable.fire),
                BitmapFactory.decodeResource(resources, R.drawable.fire),
            ), DiTabBottomInfo(
                DemoFragment::class.java,
                "收藏",
                "fonts/iconfont.ttf",
                getString(R.string.if_favorite),
                null,
                Color.parseColor("#FF656667"),
                Color.parseColor("#FFD44949")
            ), DiTabBottomInfo(
                DemoFragment::class.java,
                "我的",
                "fonts/iconfont.ttf",
                getString(R.string.if_profile),
                null,
                Color.parseColor("#FF656667"),
                Color.parseColor("#FFD44949")
            )
        )
        tabBottom.inflateInfo(dataList)
        tabBottom.select(dataList[3])
        tabBottom.findTab(dataList[2])?.resetTab(DiDisplayUtil.dp2px(66.0f))
    }
}