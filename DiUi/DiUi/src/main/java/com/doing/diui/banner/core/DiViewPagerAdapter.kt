package com.doing.diui.banner.core

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter

class DiViewPagerAdapter<E : DiBannerModel> : PagerAdapter() {

    private val mDataList = mutableListOf<E>()
    private val mCacheHolders : MutableList<DiViewPagerItemHolder> = mutableListOf()
    private var mBannerBindAdapter: (IDiBannerBindAdapter<E>)? = null
    private var mOnBannerClickListener: (IDiBannerClickListener<E>)? = null

    @LayoutRes
    private var mLayoutRes : Int = 0
    private var mIsLoop: Boolean = true

    interface IDiBannerBindAdapter<E> {
        fun onBind(holder: DiViewPagerItemHolder, model: E, position: Int)
    }

    interface IDiBannerClickListener<E> {
        fun onClick(holder: DiViewPagerItemHolder, model: E, position: Int)
    }

    fun setBannerData(@LayoutRes layoutRes: Int, dataList: List<E>) {
        val list = mDataList
        val cacheHolders = mCacheHolders
        list.clear()
        list.addAll(dataList)
        cacheHolders.clear()

        list.forEach { _ ->
            cacheHolders.add(DiViewPagerItemHolder())
        }
        this.mLayoutRes = layoutRes
        notifyDataSetChanged()
    }

    fun setBannerBindAdapter(adapter: IDiBannerBindAdapter<E>) {
        this.mBannerBindAdapter = adapter
    }

    fun setOnBannerClickListener(listener: IDiBannerClickListener<E>) {
        this.mOnBannerClickListener = listener
    }

    fun setLoop(loop: Boolean) {
        this.mIsLoop = loop
    }

    override fun getCount(): Int {
        return if (mIsLoop) { Int.MAX_VALUE } else { mDataList.size }
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    fun getFirstItem(): Int {
        return if (mIsLoop) {
            Int.MAX_VALUE / 2 - Int.MAX_VALUE / 2 % getRealCount()
        } else {
            0
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val modelList = mDataList
        val layoutId = mLayoutRes
        if (layoutId == 0) {
            return View(container.context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT)
            }
        }

        val realPosition = getRealPosition(position)

        val model = modelList[realPosition]
        val holder = mCacheHolders[realPosition]
        if (!holder.isInit) {
            holder.initView(layoutId, container)
            onClick(holder, model, realPosition)
        }

        onBind(holder, model, realPosition)
        container.addView(holder.itemView)
        return holder.itemView
    }

    private fun onClick(holder: DiViewPagerItemHolder, model: E, realPosition: Int) {
        mOnBannerClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.onClick(holder, model, realPosition)
            }
        }
    }

    private fun onBind(holder: DiViewPagerItemHolder, model: E, realPosition: Int) {
        mBannerBindAdapter?.onBind(holder, model, realPosition)
    }

    private fun getRealPosition(position: Int): Int {
        val realCount = getRealCount()
        return if (realCount > 0) {
            position % realCount
        } else {
            position
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val realPosition = getRealPosition(position)
        val holder = mCacheHolders[realPosition]

        if (holder.isInit) {
            container.removeView(holder.itemView)
        }
//        mCacheHolders[realPosition] = DiViewPagerItemHolder()
    }

    private fun getRealCount(): Int {
        return mDataList.size
    }

    class DiViewPagerItemHolder {

        var isInit = false
            private set

        lateinit var itemView: View
            private set

        private val mViews : SparseArray<View?> = SparseArray()

        fun initView(@LayoutRes layoutId: Int, parent: ViewGroup) {
            val rootView = LayoutInflater.from(parent.context).inflate(layoutId,
                parent, false)

            this.itemView = rootView
            this.isInit = true
        }

        fun <T : View> find(@IdRes id: Int): T {
            val view = mViews.get(id)
            return if (view == null) {
                itemView.findViewById<T>(id).apply {
                    mViews.put(id, this)
                }
            } else {
                view as T
            }
        }
    }

}