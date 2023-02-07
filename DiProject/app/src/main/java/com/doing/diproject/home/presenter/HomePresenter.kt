package com.doing.diproject.home.presenter

import com.doing.diproject.home.model.Category
import com.doing.diproject.home.fragment.HomeFragment
import com.doing.diproject.home.service.HomeService
import com.doing.diproject.net.ApiFactory
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiResponse

class HomePresenter {

    val service by lazy {
        ApiFactory.create(HomeService::class.java)
    }

    fun requestCategory(fragment: HomeFragment) {
        service.getCategory().enqueue(
            object : DiCallback<List<Category>> {

                override fun onSuccess(response: DiResponse<List<Category>>) {
                    if (response.isSuccess() && response.data != null) {
                        fragment.onCategoryResponse(response.data)
                    }
                }
        })
    }
}