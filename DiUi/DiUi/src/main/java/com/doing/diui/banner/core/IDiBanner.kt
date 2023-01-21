package com.doing.diui.banner.core

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.doing.diui.R
import com.doing.diui.banner.indicator.IDiBannerIndicator

interface IDiBanner<E : DiBannerModel> {

    fun setBannerData(@LayoutRes layout: Int = R.layout.layout_banner_item_default,
                      models: List<E>)

    fun setIndicator(indicator: IDiBannerIndicator)

    fun setAutoPlay(autoPlay: Boolean)

    fun setLoop(loop: Boolean)

    fun setIntervalTime(interval: Long)

    fun setBindAdapter(adapter: DiViewPagerAdapter.IDiBannerBindAdapter<E>)

    fun setOnBannerCLickListener(listener: DiViewPagerAdapter.IDiBannerClickListener<E>)

    fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener)

    fun setScrollDuration(duration: Int)
}