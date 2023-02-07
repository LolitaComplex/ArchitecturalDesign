package com.doing.diui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class DiHolderItem<DATA, Holder : RecyclerView.ViewHolder>(private val data: DATA ) {

    private lateinit var mAdapter: DiAdapter

    abstract fun onBindView(holder: RecyclerView.ViewHolder, position: Int)

    open fun layoutId(): Int {
        return -1
    }

    open fun getLayoutView(container: ViewGroup): View {
        val layoutId = layoutId()
        if (layoutId != -1) {
            return LayoutInflater.from(container.context).inflate(
                layoutId, container, false)
        }
        return View(container.context)
    }

    fun refreshItem() {
        mAdapter.refreshItem(this)
    }

    fun removeItem() {
        mAdapter.removeItem(this)
    }

    fun setAdapter(adapter: DiAdapter) {
        this.mAdapter = adapter
    }

    fun getSpanCount(): Int {
        return 0
    }
}