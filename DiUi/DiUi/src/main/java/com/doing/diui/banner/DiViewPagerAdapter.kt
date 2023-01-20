package com.doing.diui.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class DiViewPagerAdapter<E : DiBannerModel> : PagerAdapter() {

    private val mDataList: MutableList<E> = mutableListOf()
    private val mCacheHolders : MutableList<DiViewPagerItemHolder<E>> = mutableListOf()

    fun setBannerData(dataList: List<E>) {
        val list = mDataList
        val cacheHolders = mCacheHolders
        list.clear()
        list.addAll(dataList)
        cacheHolders.clear()

        list.forEach { data ->
            cacheHolders.add(DiViewPagerItemHolder(data))
        }
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mDataList.size;
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val realPosition = getRealPosition(position)

        val holder = mCacheHolders[realPosition]
        if (holder.isInit) {
            holder.initView(container)
        }

        holder.bind()
        container.addView(holder.itemView)
        return holder.itemView
    }

    private fun getRealPosition(position: Int): Int {
        val realCount = getRealCount()
        val realPosition = if (realCount > 0) {
            position % realCount
        } else {
            position
        }
        return realPosition
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val realPosition = getRealPosition(position)
        val holder = mCacheHolders[realPosition]

        container.removeView(holder.itemView)
        mCacheHolders[realPosition] = DiViewPagerItemHolder(mDataList[realPosition])
    }

    private fun getRealCount(): Int {
        return mDataList.size
    }

    private class DiViewPagerItemHolder<E>(private val data: DiBannerModel) {

        var isInit = false
            private set

        lateinit var itemView: View
            private set

        fun initView(parent: ViewGroup) {
            val rootView = LayoutInflater.from(parent.context).inflate(data.layoutId,
                parent, false)

            this.itemView = rootView
            this.isInit = true
        }

        fun bind() {

        }
    }

}