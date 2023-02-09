package com.doing.diproject.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.doing.diproject.R
import com.doing.diproject.detail.model.Detail
import com.doing.diproject.home.model.HomeList
import com.doing.diproject.router.RouterConstant
import com.doing.diui.adapter.DiAdapter
import com.doing.diui.common.DiStatusBar
import kotlinx.android.synthetic.main.activity_detail.*

@Route(path = RouterConstant.ROUTE_ACTIVITY_DETAIL_MAIN)
class DetailActivity : AppCompatActivity() {

    @JvmField
    @Autowired
    var goodsId: String = ""

    @JvmField
    @Autowired
    var goodsModel: HomeList.GoodsList? = null

    private val mViewModel by lazy {
        DetailViewModel.get(goodsId, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        DiStatusBar.setStatusBar(this, true,
            statusBarColor = Color.TRANSPARENT, translucent = true)

        ARouter.getInstance().inject(this)

        initView()
    }

    private fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        val adapter = DiAdapter()
        recycler_view.adapter = adapter

        val liveData = mViewModel.requestDetail()
        liveData.observe(this) { detail ->

        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return super.onRetainCustomNonConfigurationInstance()
    }

    fun onRequestDetailSuccess(detail: Detail) {
    }
}