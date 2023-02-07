package com.doing.dicommon.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.doing.dicommon.R
import com.doing.diui.adapter.DiAdapter

class DiRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    private var isLoadingMore = false
    private val mFooterView by lazy {
        LayoutInflater.from(context).inflate(R.layout.layout_footer_loading_item,
            this@DiRecyclerView, false)
    }

    private var mLoadMoreScrollListener : LoadMoreScrollListener? = null

    inner class LoadMoreScrollListener(
        private val prefetchSize: Int, private val callback: () -> Unit
    ) : OnScrollListener() {
        private val adapter = getAdapter() as DiAdapter

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            if (isLoadingMore) {
                return
            }

            val totalCount = adapter.itemCount
            if (totalCount <= 0) {
                return
            }

            // 如果RecyclerView能够向下滑动，那么就能添加FooterView了
            val canScrollVertically = canScrollVertically(1) // 大于0向下滑动，小于0向上滑动

            // 当分页请求失败时，也能再次走到这里，这时候并不能滑动（已经到达底部）
            val lastVisibleItem = findLastVisibleItem(recyclerView)
            if (lastVisibleItem < 0) {
                return
            }

            val isArriveBottom = lastVisibleItem > totalCount - 1
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING &&
                (canScrollVertically || isArriveBottom)) {
                addFooterView()
            }

            if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                return
            }

            // 预加载，不需要等待滑动到最后一个Item时再触发 接口请求，提前请求
            val isStartRequest = totalCount - lastVisibleItem <= prefetchSize
            if (!isStartRequest) {
                return
            }

            isLoadingMore = true
            callback.invoke()
        }


        private fun addFooterView() {
            if (mFooterView.parent != null) {
                mFooterView.post {
                    adapter.addFooter(mFooterView)
                }
            } else {
                adapter.addFooter(mFooterView)
            }
        }


        private fun findLastVisibleItem(recyclerView: RecyclerView): Int {
            return when (val manager = recyclerView.layoutManager) {
                is LinearLayoutManager -> manager.findLastVisibleItemPosition()
                is StaggeredGridLayoutManager -> {
                    manager.findFirstVisibleItemPositions(null)[0]
                }
                else -> -1
            }
        }
    }

    fun enableLoadMore(prefetchSize: Int, callback: () -> Unit) {
        if (adapter !is DiAdapter) {
            return
        }

        if (mLoadMoreScrollListener != null) {
            return
        }

        val loadMoreScrollListener = LoadMoreScrollListener(prefetchSize, callback)
        addOnScrollListener(loadMoreScrollListener)
        mLoadMoreScrollListener = loadMoreScrollListener
    }

    fun disableLoadMore() {
        if (adapter !is DiAdapter) {
            return
        }

        val footerView = mFooterView
        if (footerView.parent != null) {
            (adapter as DiAdapter).removeFooter(footerView)
        }

        val listener = mLoadMoreScrollListener
        if (listener != null) {
            removeOnScrollListener(listener)
        }
        mLoadMoreScrollListener = null
        isLoadingMore = false
    }

    fun isLoading(): Boolean {
        return isLoadingMore
    }

    fun loadFinished(success: Boolean) {
        if (adapter !is DiAdapter) {
            return
        }

        isLoadingMore = false
        val adapter = adapter as DiAdapter
        val footerView = mFooterView
        if (footerView.parent != null) {
            adapter.removeFooter(footerView)
        }
    }
}