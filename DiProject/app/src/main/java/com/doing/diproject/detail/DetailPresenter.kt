package com.doing.diproject.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.doing.diproject.detail.model.Detail


class DetailPresenter(private val detailActivity: DetailActivity) {

    private val mViewModel by lazy { DetailViewModel.get(detailActivity) }

    fun requestDetail(goodsId: String) {
        val liveData = mViewModel.requestDetail(goodsId)

        liveData.observe(detailActivity, object : Observer<Detail?> {
            override fun onChanged(detail: Detail?) {
                if (detail != null) {
                    detailActivity.onRequestDetailSuccess(detail)
                } else {
                    detailActivity.onRequestError()
                }

                liveData.removeObserver(this)
            }
        })
    }
}