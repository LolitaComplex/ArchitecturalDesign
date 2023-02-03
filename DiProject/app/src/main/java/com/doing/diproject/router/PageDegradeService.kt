package com.doing.diproject.router

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter


@Route(path = RouterConstant.ROUTE_ACTIVITY_ROUTER_SERVICE)
class PageDegradeService : DegradeService {
    override fun init(context: Context?) {

    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        ARouter.getInstance().build(RouterConstant.ROUTE_ACTIVITY_ROUTER_ERROR)
            .greenChannel().navigation()
    }
}