package com.doing.diproject.home.fragment

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.doing.diproject.R
import com.doing.diproject.common.DiBaseFragment
import com.doing.diproject.home.model.Profile
import com.doing.diproject.home.model.ProfilePresenter
import com.doing.diui.banner.DiBanner
import com.doing.diui.banner.core.DiBannerModel
import com.doing.diui.banner.core.DiViewPagerAdapter
import com.doing.diui.banner.core.DiViewPagerAdapter.DiViewPagerItemHolder
import com.doing.diui.banner.core.DiViewPagerAdapter.IDiBannerBindAdapter
import com.doing.diui.view.iconfont.IconFontTextView

class ProfileFragment : DiBaseFragment() {

    private lateinit var mTvDesc: TextView
    private lateinit var mTvUserName: TextView
    private lateinit var mIvAvatar: ImageView
    private lateinit var mBanner: DiBanner<DiBannerModel>

    private val mPresenter = ProfilePresenter(this)

    companion object {
        const val ITEM_SPACE = "   "
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun initView(parent: View) {
        parent.findViewById<IconFontTextView>(R.id.item_course).apply {
            this.text = getString(R.string.if_notify)
            this.append("${ITEM_SPACE}课程公告")
        }

        parent.findViewById<IconFontTextView>(R.id.item_adress).apply {
            this.text = getString(R.string.if_address)
            this.append("${ITEM_SPACE}我的收藏")
        }

        parent.findViewById<IconFontTextView>(R.id.item_collection).apply {
            this.text = getString(R.string.if_collection)
            this.append("${ITEM_SPACE}收货地址")
        }

        parent.findViewById<IconFontTextView>(R.id.item_history).apply {
            this.text = getString(R.string.if_history)
            this.append("${ITEM_SPACE}浏览历史")
        }

        mIvAvatar = parent.findViewById(R.id.profile_iv_avatar)
        mTvDesc = parent.findViewById(R.id.profile_tv_desc)
        mTvUserName = parent.findViewById(R.id.profile_tv_username)
        mBanner = parent.findViewById<DiBanner<DiBannerModel>>(R.id.profile_banner)

        mPresenter.requestProfile()
    }

    fun onRequestProfileSuccess(profile: Profile) {
        val banner = mBanner
        if (profile.isLogin) {
            mTvUserName.text = profile.userName
            Glide.with(requireContext()).load(profile.userIcon).into(mIvAvatar)
        }

        val list = mutableListOf<DiBannerModel>()
        profile.bannerNoticeList.forEach { item ->
            list.add(DiBannerModel(DiBannerModel.Type.Url(item.url)))
        }
        banner.setBindAdapter(object : IDiBannerBindAdapter<DiBannerModel> {
            override fun onBind(holder: DiViewPagerItemHolder, model: DiBannerModel,
                position: Int) {
                val imageView = holder.find<ImageView>(R.id.iv_banner_cover)
                when (val type = model.type) {
                    is DiBannerModel.Type.Url -> {
                        Glide.with(context!!).load(type.url).into(imageView)
                    }
                    else -> {}
                }
            }
        })
        banner.setBannerData(R.layout.layout_profile_banner_item, list)
        banner.visibility = View.VISIBLE
    }

}