package com.doing.diproject.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.doing.diproject.detail.model.Detail
import com.doing.diproject.detail.service.DetailService
import com.doing.diproject.net.ApiFactory
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiHttpException
import com.doing.hilibrary.restful.DiResponse

class DetailViewModel private constructor() : ViewModel() {

    private val service by lazy { ApiFactory.create(DetailService::class.java) }
    private val pageData by lazy {  MutableLiveData<Detail?>() }

    companion object {
        private class DetailViewModelFactory() :
            ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return try {
                    modelClass.getConstructor(DetailActivity::class.java).newInstance()
                } catch (e: Exception) {
                    super.create(modelClass)
                }
            }
        }

        fun get(viewModelStoreOwner: ViewModelStoreOwner): DetailViewModel {
            return ViewModelProvider(viewModelStoreOwner, DetailViewModelFactory())
                .get(DetailViewModel::class.java)
        }
    }

    fun requestDetail(goodsId: String): MutableLiveData<Detail?> {
        service.getDetailContent(goodsId).enqueue(object : DiCallback<Detail> {
            override fun onSuccess(response: DiResponse<Detail>) {
                if (response.isSuccess() && response.data != null) {
                    pageData.postValue(response.data)
                } else {
                    pageData.postValue(null)
                }
            }

            override fun onFailure(exception: DiHttpException) {
                pageData.postValue(null)
            }
        })

        return pageData
    }

}