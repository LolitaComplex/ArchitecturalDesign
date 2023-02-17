package com.doing.diproject.home.fragment.home

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
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

    private class DiffCallback(private val oldList: List<HomeList.BannerList>,
                       private val newList: List<HomeList.BannerList>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return null
        }



    }
}