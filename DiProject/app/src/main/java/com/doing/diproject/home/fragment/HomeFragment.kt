package com.doing.diproject.home.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.alibaba.android.arouter.launcher.ARouter
import com.doing.diproject.R
import com.doing.diproject.account.AccountConstant
import com.doing.dicommon.DiBaseFragment

class HomeFragment : DiBaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.HomeFragment_btn_request).setOnClickListener {
//            ApiFactory.create(TestService::class.java).getCities("beijing")
//                .enqueue(object : DiCallback<String> {
//                    override fun onSuccess(response: DiResponse<String>) {
//                        DiLog.d("Doing", "${response.data?.toString()}")
//                    }
//                })
//            ARouter.getInstance()
//                .build(AccountConstant.ROUTE_ACTIVITY_LOGIN)
//                .navigation()
            val clazz = Class.forName("com.doing.didebugtool.DebugToolDialog")
            val dialogFragment = clazz.getConstructor().newInstance() as DialogFragment
            dialogFragment.show(childFragmentManager, "debug_tool")
        }
    }
}