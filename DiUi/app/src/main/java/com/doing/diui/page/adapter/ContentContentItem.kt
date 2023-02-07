package com.doing.diui.page.adapter

import android.graphics.Color
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doing.diui.R
import com.doing.diui.adapter.DiHolderItem
import com.doing.diui.adapter.DiViewHolder

class ContentContentItem(val data: DataItem) : DiHolderItem<DataItem, DiViewHolder>(data) {

    override fun getLayoutId(): Int {
        return R.layout.layout_adapter_item
    }

    override fun onBindView(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(Color.GRAY)
        val tvContent = holder.itemView.findViewById<TextView>(R.id.tvContent)
        tvContent.text = data.content
    }

    override fun getSpanCount(): Int {
        return 1
    }
}