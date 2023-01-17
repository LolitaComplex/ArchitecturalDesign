package com.doing.diui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.doing.diui.tab.top.DiTabTopItemInfo
import com.doing.diui.tab.top.DiTabTopView


class DiTabTopActivity : AppCompatActivity() {

    var tabsStr = arrayOf(
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "装修",
        "运动"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_di_tab_top)

        val tabTopView = findViewById<DiTabTopView>(R.id.DiTabTopActivity_tab_top)
        val defaultColor = ContextCompat.getColor(this, R.color.tabBottomDefaultColor)
        val tintColor = ContextCompat.getColor(this, R.color.tabBottomTintColor)
        val topInfoList = mutableListOf<DiTabTopItemInfo>()
        tabsStr.forEach { tab ->
            topInfoList += DiTabTopItemInfo(DemoFragment::class.java, tab, defaultColor, tintColor)
        }
        tabTopView.inflateInfo(topInfoList)
        tabTopView.select(topInfoList[0])
    }
}