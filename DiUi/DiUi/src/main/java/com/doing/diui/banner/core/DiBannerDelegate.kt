package com.doing.diui.banner.core

import android.view.ViewGroup.LayoutParams
import androidx.viewpager.widget.ViewPager
import com.doing.diui.banner.DiBanner
import com.doing.diui.banner.indicator.IDiBannerIndicator

class DiBannerDelegate<E : DiBannerModel>(private val banner: DiBanner<E>) : IDiBanner<E> {

    private val mModels = mutableListOf<E>()
    private val mViewPager by lazy {
        DiViewPager(banner.context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        }
    }

    private val mPagerAdapter by lazy { DiViewPagerAdapter<E>() }
    private var mBannerBindAdapter: (DiViewPagerAdapter.IDiBannerBindAdapter<E>)? = null
    private var mOnBannerClickListener: (DiViewPagerAdapter.IDiBannerClickListener<E>)? = null
    private var mPageChangeListener: (ViewPager.OnPageChangeListener)? = null
    private var mIsAutoPlay = false
    private var mIsLoop = true
    private var mIntervalTime = 2000L


    override fun setBannerData(layout: Int, models: List<E>) {
        val list = mModels

        list.clear()
        list.addAll(models)

        initView(layout, models)
    }

    private fun initView(layoutRes: Int, models: List<E>) {
        val pager = mViewPager
        val adapter = mPagerAdapter
        val bindAdapter = mBannerBindAdapter
        val clickListener = mOnBannerClickListener
        val pageChangeListener = mPageChangeListener

        adapter.setBannerData(layoutRes, models)
        adapter.setLoop(mIsLoop)
        pager.setIntervalTime(mIntervalTime)
        pager.setAutoPlay(mIsAutoPlay)
        pager.adapter = adapter

        if (bindAdapter != null) {
            adapter.setBannerBindAdapter(bindAdapter)
        }

        if (clickListener != null) {
            adapter.setOnBannerClickListener(clickListener)
        }

        if (pageChangeListener != null) {
            pager.addOnPageChangeListener(pageChangeListener)
        }

        banner.addView(pager)
    }

    override fun setIndicator(indicator: IDiBannerIndicator) {
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        this.mIsAutoPlay = autoPlay
    }

    override fun setLoop(loop: Boolean) {
        this.mIsLoop = loop
    }

    override fun setIntervalTime(interval: Long) {
        this.mIntervalTime = interval
    }

    override fun setBindAdapter(adapter: DiViewPagerAdapter.IDiBannerBindAdapter<E>) {
        this.mBannerBindAdapter = adapter
    }

    override fun setOnBannerCLickListener(listener: DiViewPagerAdapter.IDiBannerClickListener<E>) {
        this.mOnBannerClickListener = listener
    }

    override fun setOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
        this.mPageChangeListener = listener
    }

    override fun setScrollDuration(duration: Int) {
    }



}