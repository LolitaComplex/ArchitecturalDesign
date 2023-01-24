package com.doing.diui.page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.doing.diui.R
import com.doing.hilibrary.log.DiLog

class PagerItemFragment : Fragment() {

    private var position = 0

    companion object {
        fun newInstance(position: Int): Fragment {
            return PagerItemFragment().apply {
                this.init(position)
            }
        }
    }

    private fun init(position: Int) {
        this.position = position
    }

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_view_pager2_item,
            container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.PagerItemFragment_tv_content).let { tvContent ->
            tvContent.text = "这是第 $position 页"
        }
    }

    override fun onResume() {
        super.onResume()
        DiLog.d("Doing", "PagerItemFragment$position onResume")
    }

    override fun onPause() {
        super.onPause()
        DiLog.d("Doing", "PagerItemFragment$position onPause")
    }
}