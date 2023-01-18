package com.doing.diui.refresh.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.doing.diui.R

class DiOverTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : DiOverView(context, attrs) {

    private lateinit var mTvText: TextView

    override fun init() {
        LayoutInflater.from(context).inflate(R.layout.layout_over_text_view,
            this, true)
        mTvText = findViewById<TextView>(R.id.DiOverTextView_tv_text)
    }

    override fun onVisible() {
        mTvText.text = "待刷新"
    }

    override fun onOver() {
        mTvText.text = "松手即可刷新 "
    }

    override fun onRefresh() {
        mTvText.text = "刷新中…… "
    }

    override fun onFinish() {
        mTvText.text = "刷新完毕"
    }

    override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {
    }
}