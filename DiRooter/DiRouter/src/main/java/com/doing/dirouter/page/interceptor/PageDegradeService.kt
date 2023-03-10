package com.doing.dirouter.page.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter
import com.doing.dirouter.RouterConstant


@Route(path = RouterConstant.ACTIVITY_ROUTER_SERVICE)
class PageDegradeService : DegradeService {
    override fun init(context: Context?) {

    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        ARouter.getInstance().build(RouterConstant.ACTIVITY_ROUTER_ERROR)
            .greenChannel().navigation()
    }
}