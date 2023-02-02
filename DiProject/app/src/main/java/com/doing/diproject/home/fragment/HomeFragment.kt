package com.doing.diproject.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.doing.diproject.R
import com.doing.diproject.common.DiBaseFragment
import com.doing.diproject.account.LoginActivity

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
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}