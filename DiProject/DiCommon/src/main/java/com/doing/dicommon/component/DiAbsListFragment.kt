package com.doing.dicommon.component

import android.view.View
import androidx.annotation.CallSuper
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doing.dicommon.R
import com.doing.dicommon.view.DiRecyclerView
import com.doing.diui.adapter.DiAdapter
import com.doing.diui.adapter.DiHolderItem
import com.doing.diui.refresh.common.IDiRefresh
import com.doing.diui.refresh.view.DiOverTextView
import com.doing.diui.refresh.view.DiOverView
import com.doing.diui.refresh.view.DiRefreshView
import com.doing.diui.view.error.EmptyView
import kotlinx.android.synthetic.main.fragment_abs_list.*

open class DiAbsListFragment : DiBaseFragment(), IDiRefresh.OnRefreshListener {

    private var pageIndex: Int = 1
    private lateinit var mRecyclerView: DiRecyclerView
    private lateinit var mRefreshView: DiRefreshView
    private lateinit var mRefreshHeader: DiOverView
    private lateinit var mEmptyView: EmptyView
    private lateinit var mProgressBar: ContentLoadingProgressBar
    private lateinit var mAdapter: DiAdapter

    companion object {
        const val PREFETCH_SIZE = 5
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_abs_list
    }

    override fun initView(view: View) {
        val refreshHeader = DiOverTextView(view.context)
        refresh_layout.setRefreshOverView(refreshHeader)
        refresh_layout.setOnRefreshListener(this)

        val manager = getLayoutManager()
        val adapter = DiAdapter()

        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter

        empty_view.apply {
            visibility = View.GONE
            setIcon(R.string.list_empty)
            setDesc(getString(R.string.list_empty_desc))
            setButton(getString(R.string.list_empty)) { _ ->
                onRefresh()
            }
        }

        content_loading.visibility = View.VISIBLE


        this.mRecyclerView = recycler_view
        this.mRefreshView = refresh_layout
        this.mEmptyView = empty_view
        this.mProgressBar = content_loading
        this.mAdapter = adapter
        this.mRefreshHeader = refreshHeader
        this.pageIndex = 1
    }

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun finishRefresh(dataList: List<DiHolderItem<*, out RecyclerView.ViewHolder>>?) {
        val isSuccess = dataList != null && dataList.isNotEmpty()
        val adapter = mAdapter


        val refresh = pageIndex == 1
        if (refresh) {
            mProgressBar.visibility = View.GONE
            mRefreshView.refreshFinished()
            if (isSuccess) {
                adapter.clearItems()
                adapter.addHolderItems(dataList!!)
            } else {
                if (adapter.itemCount <= 0) {
                    mEmptyView.visibility = View.VISIBLE
                }
            }
        } else {
            if (isSuccess) {
                adapter.addHolderItems(dataList!!)
            }
            mRecyclerView.loadFinished(isSuccess)
        }
    }

    fun setLoadMoreListener(callback: () -> Unit) {
        val recyclerView = mRecyclerView
        val header = mRefreshHeader

        recyclerView.enableLoadMore(PREFETCH_SIZE) {
            if (header.getState() == DiOverView.DiRefreshState.STATE_REFRESH) {
                recyclerView.loadFinished(false)
                return@enableLoadMore
            }
            pageIndex++
            callback.invoke()
        }
    }

    @CallSuper
    override fun onRefresh() {
        val recyclerView = mRecyclerView
        val refreshView = mRefreshView

        if (recyclerView.isLoading()) {
            refreshView.post {
                refreshView.refreshFinished()
            }
        }

        pageIndex = 1
    }

    override fun enableRefresh(): Boolean {
        return true
    }
}