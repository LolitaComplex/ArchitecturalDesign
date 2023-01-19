package com.doing.diui.refresh.demo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.doing.diui.R

class DiDemoRefreshHeadTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : DiDemoRefreshHeadView(context, attrs) {

    private lateinit var mTvContent: TextView

    override fun init() {
        LayoutInflater.from(context).inflate(R.layout.layout_over_text_view,
            this, true)
        mTvContent = findViewById(R.id.DiOverTextView_tv_text)
    }

    override fun onVisible() {
        mTvContent.text = "下拉刷新"
    }

    override fun onRefresh() {
        mTvContent.text = "刷新中"
    }

    override fun onOver() {
        mTvContent.text = "释放刷新"
    }

    override fun onScroll(distance: Int) {
    }

}