package com.doing.diproject.home.fragment.home

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.doing.dicommon.component.DiAbsListFragment
import com.doing.diproject.home.model.HomeList
import com.doing.diproject.home.presenter.HomePresenter
import com.doing.diui.adapter.DiHolderItem

class HomeTabFragment : DiAbsListFragment() {

    private lateinit var categoryId: String
    private val mPresenter by lazy { HomePresenter() }

    companion object {

        @JvmStatic
        fun newInstance(categoryId: String): Fragment {
            return HomeTabFragment().apply {
                init(categoryId)
            }
        }
    }

    private fun init(categoryId: String) {
        this.categoryId = categoryId
    }

    override fun initView(view: View) {
        super.initView(view)
        mPresenter.requestCategoryList(categoryId, this)
    }

    fun onHomeListSuccess(homeList: HomeList) {
        val dataItems =
            mutableListOf<DiHolderItem<*, out RecyclerView.ViewHolder>>()

        if (homeList.bannerList != null) {
            dataItems.add(BannerHolderItem(homeList.bannerList))
        }

        if (homeList.subcategoryList != null) {
            dataItems.add(GridHolderItem(homeList.subcategoryList))
        }

        homeList.goodsList?.forEach { good ->
            dataItems.add(GoodsHolderItem(good, true))
        }

        finishRefresh(dataItems)
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresenter.requestCategoryList(categoryId, this)
    }
}