package com.doing.diproject.home.fragment.home

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doing.diproject.R
import com.doing.diproject.home.MainActivity
import com.doing.diproject.home.model.HomeList
import com.doing.diui.adapter.DiHolderItem
import com.doing.diui.adapter.DiViewHolder
import com.doing.diui.banner.DiBanner
import com.doing.diui.banner.core.DiBannerModel
import com.doing.diui.banner.core.DiViewPagerAdapter
import com.doing.diui.banner.core.DiViewPagerAdapter.DiViewPagerItemHolder
import com.doing.diui.banner.core.DiViewPagerAdapter.IDiBannerBindAdapter
import com.doing.hilibrary.log.DiLog
import com.doing.hilibrary.util.DiDisplayUtil

class BannerHolderItem(private val bannerList: List<HomeList.BannerList>)
    : DiHolderItem<List<HomeList.BannerList>, DiViewHolder>(bannerList) {

    private var isBind = false

    override fun getLayoutView(container: ViewGroup): View {
        return DiBanner<DiBannerModel>(container.context).apply {
            val params = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DiDisplayUtil.dp2px(160.0f))
            params.bottomMargin = 10
            layoutParams = params
        }
    }

    override fun onBindView(holder: RecyclerView.ViewHolder, position: Int) {
        val banner = holder.itemView as DiBanner<DiBannerModel>

        val time = System.currentTimeMillis() - MainActivity.start
        DiLog.d(MainActivity.TAG, "addOnPreDrawListener bind data: ${time}ms")

        holder.itemView.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {

            override fun onPreDraw(): Boolean {
                val timeInner = System.currentTimeMillis() - MainActivity.start
                DiLog.d(MainActivity.TAG, "addOnPreDrawListener: ${timeInner}ms")
                holder.itemView.viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })

        val count = banner.childCount
        for (i in count - 1 .. 0) {
            banner.setAutoPlay(false)
            banner.removeView(banner.getChildAt(i))
        }

        banner.setBindAdapter(object : IDiBannerBindAdapter<DiBannerModel> {
            override fun onBind(holder: DiViewPagerItemHolder, model: DiBannerModel, position: Int) {
                val ivCover = holder.find<ImageView>(R.id.iv_banner_cover)
                when (val type = model.type) {
                    is DiBannerModel.Type.Url -> {
                        Glide.with(ivCover.context).load(type.url).into(ivCover)
                    }
                    else -> {}
                }
            }

        })
        banner.setLoop(true)
        banner.setAutoPlay(true)
        val list = mutableListOf<DiBannerModel>()
        bannerList.forEach { item ->
            list.add(DiBannerModel(DiBannerModel.Type.Url(item.cover)))
        }
        banner.setBannerData(R.layout.layout_profile_banner_item, list)
        isBind = true
    }
}