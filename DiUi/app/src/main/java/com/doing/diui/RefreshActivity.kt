package com.doing.diui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doing.diui.refresh.common.IDiRefresh
import com.doing.diui.refresh.view.DiOverTextView
import com.doing.diui.refresh.view.DiRefreshView
import com.doing.hilibrary.log.DiLog

class RefreshActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh)

        val refreshView = findViewById<DiRefreshView>(R.id.RefreshActivity_rv_refresh)
        refreshView.setRefreshOverView(DiOverTextView(this))
        refreshView.setOnRefreshListener(object : IDiRefresh.OnRefreshListener {
            override fun onRefresh() {
                DiLog.d("刷新中……")
                refreshView.postDelayed({
                    refreshView.refreshFinished()
                }, 1000)
            }

            override fun enableRefresh(): Boolean {
                return true
            }

        })

        val dataList = mutableListOf<String>()
        for (i in 0 until 100) {
            dataList.add("Item测试文本: $i")
        }

        val recyclerView = findViewById<RecyclerView>(R.id.RefreshActivity_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)

        recyclerView.adapter = object : RecyclerView.Adapter<RefreshItemHolder> () {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefreshItemHolder {
                val itemView = LayoutInflater.from(this@RefreshActivity).inflate(
                    R.layout.layout_refresh_item, parent, false)
                return RefreshItemHolder(itemView)
            }

            override fun onBindViewHolder(holder: RefreshItemHolder, position: Int) {
                holder.bindView(dataList[position])
            }

            override fun getItemCount(): Int {
                return dataList.size
            }
        }
    }

    private class RefreshItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvContent = itemView.findViewById<TextView>(R.id.RefreshItem_tv_content)

        fun bindView(text: String) {
            tvContent.text = text
        }

    }
}