package com.doing.diproject.router

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.doing.diproject.R
import com.doing.diproject.common.DiBaseActivity
import com.doing.diui.view.error.EmptyView


@Route(path = RouterConstant.ROUTE_ACTIVITY_ROUTER_ERROR)
class DegradeGlobalActivity : DiBaseActivity() {

    companion object {
        const val TITLE = "degrade_title"
        const val DESC = "degrade_desc"
        const val ACTION = "degrade_action"
    }

    @JvmField
    @Autowired(name = TITLE)
    var mTitle = ""
    @JvmField
    @Autowired(name = DESC)
    var mDesc = ""
    @JvmField
    @Autowired(name = ACTION)
    var mAction = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_degrage)
        ARouter.getInstance().inject(this)

        val tvBack = findViewById<TextView>(R.id.DegradeGlobalActivity_tv_back)
        tvBack.setText(R.string.if_back_soild)
        tvBack.setOnClickListener {
            onBackPressed()
        }

        val emptyView = findViewById<EmptyView>(R.id.DegradeGlobalActivity_empty_view)
        val title = mTitle
        if (title.isNotEmpty()) {
            emptyView.setTitle(title)
        }
        val desc = mDesc
        if (desc.isNotEmpty()) {
            emptyView.setDesc(desc)
        }

        val action = mAction
        if (action.isNotEmpty()) {
            emptyView.setHelpAction(listener = { _ ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(action)))
            })
        }
        emptyView.setIcon(R.string.if_empty)
    }
}