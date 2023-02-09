package com.doing.diproject.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.doing.diproject.detail.model.Detail
import com.doing.diproject.detail.service.DetailService
import com.doing.diproject.net.ApiFactory
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiResponse

class DetailViewModel private constructor(private val view: DetailActivity) : ViewModel() {

    private val service by lazy { ApiFactory.create(DetailService::class.java) }

    companion object {
        private class DetailViewModelFactory(private val view : DetailActivity) :
            ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return try {
                    modelClass.getConstructor(DetailActivity::class.java).newInstance(view)
                } catch (e: Exception) {
                    super.create(modelClass)
                }
            }
        }

        fun get(view: DetailActivity, viewModelStoreOwner: ViewModelStoreOwner): DetailViewModel {
            return ViewModelProvider(viewModelStoreOwner, DetailViewModelFactory(view))
                .get(DetailViewModel::class.java)
        }
    }

    fun requestDetail(goodsId: String) {
        val pageData = MutableLiveData<Detail>()
        service.getDetailContent(goodsId).enqueue(object : DiCallback<Detail> {
            override fun onSuccess(response: DiResponse<Detail>) {
                if (response.isSuccess() && response.data != null) {
                    pageData.postValue(response.data)
                }
            }
        })
        pageData.observe(view) { detail ->
            view.onRequestDetailSuccess(detail)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}