package com.doing.diui.page.adapter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doing.diui.R
import com.doing.diui.adapter.DiAdapter
import com.doing.diui.adapter.DiHolderItem
import kotlinx.android.synthetic.main.activity_adapter.*

class AdapterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter)

        mRecyclerView.layoutManager = GridLayoutManager(this, 2,
            LinearLayoutManager.VERTICAL, false)
        val adapter = DiAdapter()
        val tvHeadView = TextView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 400)
            setBackgroundColor(Color.YELLOW)
            text = "Header"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
            gravity = Gravity.CENTER
        }
        adapter.addHeader(tvHeadView)
        adapter.addHolderItem(item = ContentTitleItem(DataItem("我是标题")))
        adapter.addHolderItem(item = ContentTitleItem(DataItem("我是标题")))
        adapter.addHolderItem(item = ContentTitleItem(DataItem("我是标题")))
        adapter.addHolderItem(item = ContentTitleItem(DataItem("我是标题")))
        adapter.addHolderItem(item = ContentTitleItem(DataItem("我是标题")))
        adapter.addHolderItem(item = ContentContentItem(DataItem("我是内容")))
        adapter.addHolderItem(item = ContentContentItem(DataItem("我是内容")))
        adapter.addHolderItem(item = ContentContentItem(DataItem("我是内容")))
        adapter.addHolderItem(item = ContentContentItem(DataItem("我是内容")))
        adapter.addHolderItem(item = ContentContentItem(DataItem("我是内容")))
        val tvFooter = TextView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 400)
            setBackgroundColor(Color.BLUE)
            text = "Footer"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
            gravity = Gravity.CENTER
        }
        adapter.addFooter(tvFooter)
        mRecyclerView.adapter = adapter
    }
}