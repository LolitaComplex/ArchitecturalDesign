package com.doing.diui.banner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.doing.diui.banner.core.DiBannerDelegate
import com.doing.diui.banner.core.DiBannerModel
import com.doing.diui.banner.core.DiViewPagerAdapter
import com.doing.diui.banner.core.IDiBanner
import com.doing.diui.banner.indicator.IDiBannerIndicator

class DiBanner<E : DiBannerModel> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs), IDiBanner<E> {

    private val mBannerDelegate = DiBannerDelegate(this)

    override fun setBannerData(@LayoutRes layout: Int,
                               models: List<E>) {
        mBannerDelegate.setBannerData(layout, models)
    }

    override fun setIndicator(indicator: IDiBannerIndicator) {
        mBannerDelegate.setIndicator(indicator)
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        mBannerDelegate.setAutoPlay(autoPlay)
    }

    override fun setLoop(loop: Boolean) {
        mBannerDelegate.setLoop(loop)
    }

    override fun setIntervalTime(interval: Long) {
        mBannerDelegate.setIntervalTime(interval)
    }

    override fun setBindAdapter(adapter: DiViewPagerAdapter.IDiBannerBindAdapter<E>) {
        mBannerDelegate.setBindAdapter(adapter)
    }

    override fun setOnBannerCLickListener(listener: DiViewPagerAdapter.IDiBannerClickListener<E>) {
        mBannerDelegate.setOnBannerCLickListener(listener)
    }

    override fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        mBannerDelegate.setOnPageChangeListener(listener)
    }

    override fun setScrollDuration(duration: Int) {
        mBannerDelegate.setScrollDuration(duration)
    }

}