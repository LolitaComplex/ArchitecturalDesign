package com.doing.diproject.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.doing.diui.view.error.EmptyView
import kotlinx.android.synthetic.main.activity_detail.*

@Route(path = RouterConstant.ROUTE_ACTIVITY_DETAIL_MAIN)
class DetailActivity : AppCompatActivity() {

    @JvmField
    @Autowired
    var goodsId: String = ""

    @JvmField
    @Autowired
    var goodsModel: HomeList.GoodsList? = null

    private val mPresenter by lazy { DetailPresenter(this) }
    private val mEmptyView by lazy {
        EmptyView(this).apply {
            setIcon(R.string.if_empty3)
            setDesc(getString(com.doing.diui.R.string.list_empty_desc))
            layoutParams = ConstraintLayout.LayoutParams(-1, -1)
            setBackgroundColor(Color.WHITE)
            setButton(getString(R.string.list_empty_action)) {
                mPresenter.requestDetail(goodsId)
            }
        }
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

        mPresenter.requestDetail(goodsId)
    }

    fun onRequestDetailSuccess(detail: Detail) {
        root_container.removeView(mEmptyView)


    }

    fun onRequestError() {
        root_container.addView(mEmptyView)
    }
}