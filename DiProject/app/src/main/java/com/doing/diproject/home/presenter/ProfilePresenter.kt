package com.doing.diproject.home.presenter

import com.doing.diproject.home.fragment.ProfileFragment
import com.doing.diproject.home.model.Profile
import com.doing.diproject.home.service.ProfileService
import com.doing.diproject.net.ApiFactory
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiHttpException
import com.doing.hilibrary.restful.DiResponse

class ProfilePresenter(private val view: ProfileFragment) {

    private val service by lazy {
        ApiFactory.create(ProfileService::class.java)
    }

    fun requestProfile() {
        service.profile().enqueue(object : DiCallback<Profile> {
            override fun onSuccess(response: DiResponse<Profile>) {
                if (response.isSuccess() && response.data != null) {
                    view.onRequestProfileSuccess(response.data!!)
                }
            }

            override fun onFailure(exception: DiHttpException) {

            }
        })
    }
}