package com.doing.dicommon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class DiBaseFragment : Fragment() {

    private lateinit var mLayoutView: View

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected open fun initView(view: View) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false).apply {
            mLayoutView = this
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(view)
    }
}