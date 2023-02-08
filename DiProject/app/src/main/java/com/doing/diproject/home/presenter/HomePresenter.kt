package com.doing.diproject.home.presenter

import com.doing.diproject.home.model.Category
import com.doing.diproject.home.fragment.home.HomeFragment
import com.doing.diproject.home.fragment.home.HomeTabFragment
import com.doing.diproject.home.model.HomeList
import com.doing.diproject.home.service.HomeService
import com.doing.diproject.net.ApiFactory
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiResponse

class HomePresenter {

    private val service by lazy {
        ApiFactory.create(HomeService::class.java)
    }

    fun requestCategory(fragment: HomeFragment) {
        service.getCategory().enqueue(
            object : DiCallback<List<Category>> {

                override fun onSuccess(response: DiResponse<List<Category>>) {
                    if (response.isSuccess() && response.data != null) {
                        fragment.onCategorySuccess(response.data!!)
                    }
                }
        })
    }

    fun requestCategoryList(categoryId: String, fragment: HomeTabFragment) {
        service.getCategoryList(categoryId).enqueue( object : DiCallback<HomeList> {

            override fun onSuccess(response: DiResponse<HomeList>) {
                if (response.isSuccess() && response.data != null) {
                    fragment.onHomeListSuccess(response.data!!)
                }
            }
        })
    }
}