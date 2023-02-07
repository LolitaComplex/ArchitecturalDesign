package com.doing.diproject.home.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.doing.diproject.R
import com.doing.dicommon.component.DiBaseFragment
import com.doing.diproject.home.model.Profile
import com.doing.diproject.home.presenter.ProfilePresenter
import com.doing.diproject.router.RouterConstant
import com.doing.diui.banner.DiBanner
import com.doing.diui.banner.core.DiBannerModel
import com.doing.diui.banner.core.DiViewPagerAdapter.DiViewPagerItemHolder
import com.doing.diui.banner.core.DiViewPagerAdapter.IDiBannerBindAdapter
import com.doing.diui.view.iconfont.IconFontTextView

class ProfileFragment : DiBaseFragment() {

    private lateinit var mRlContainer: RelativeLayout
    private lateinit var mTvDesc: TextView
    private lateinit var mTvUserName: TextView
    private lateinit var mIvAvatar: ImageView
    private lateinit var mBanner: DiBanner<DiBannerModel>
    private lateinit var mTvCollection: TextView
    private lateinit var mTvHistory: TextView
    private lateinit var mTvLearn: TextView

    private val mPresenter = ProfilePresenter(this)

    companion object {
        const val ITEM_SPACE = "   "
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun initView(view: View) {
        view.findViewById<IconFontTextView>(R.id.item_course).apply {
            this.text = getString(R.string.if_notify)
            this.append("${ITEM_SPACE}课程公告")
        }

        view.findViewById<IconFontTextView>(R.id.item_adress).apply {
            this.text = getString(R.string.if_address)
            this.append("${ITEM_SPACE}我的收藏")
        }

        view.findViewById<IconFontTextView>(R.id.item_collection).apply {
            this.text = getString(R.string.if_collection)
            this.append("${ITEM_SPACE}收货地址")
        }

        view.findViewById<IconFontTextView>(R.id.item_history).apply {
            this.text = getString(R.string.if_history)
            this.append("${ITEM_SPACE}浏览历史")
        }

        mRlContainer = view.findViewById(R.id.profile_rl_container)
        mIvAvatar = view.findViewById(R.id.profile_iv_avatar)
        mTvDesc = view.findViewById(R.id.profile_tv_desc)
        mTvUserName = view.findViewById(R.id.profile_tv_username)

        mTvCollection = view.findViewById(R.id.tab_item_collection)
        mTvHistory = view.findViewById(R.id.tab_item_history)
        mTvLearn = view.findViewById(R.id.tab_item_learn)

        mBanner = view.findViewById(R.id.profile_banner)

        mPresenter.requestProfile()
    }

    fun onRequestProfileSuccess(profile: Profile) {
        val banner = mBanner
        if (profile.isLogin) {
            mTvUserName.text = profile.userName
            Glide.with(requireContext()).load(profile.userIcon).into(mIvAvatar)
        } else {
            mTvUserName.text = "请先登录"
            mTvDesc.text = "解锁全部功能，畅享架构师专属福利"
            mRlContainer.setOnClickListener {
                ARouter.getInstance().build(RouterConstant.ROUTE_ACTIVITY_LOGIN).navigation()
            }
        }

        val list = mutableListOf<DiBannerModel>()
        profile.bannerNoticeList.forEach { item ->
            list.add(DiBannerModel(DiBannerModel.Type.Url(item.cover)))
        }

        mTvCollection.text =
            spannableTabItem(
                profile.favoriteCount,
                getString(R.string.profile_tab_item_collection)
            )
        mTvHistory.text =
            spannableTabItem(profile.browseCount, getString(R.string.profile_tab_item_history))
        mTvLearn.text =
            spannableTabItem(profile.learnMinutes, getString(R.string.profile_tab_item_learn))

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
        banner.setLoop(true)
        banner.setAutoPlay(true)
        banner.setBannerData(R.layout.layout_profile_banner_item, list)
        banner.visibility = View.VISIBLE
    }

    private fun spannableTabItem(topText: Int, bottomText: String): CharSequence? {
        val spanStr = topText.toString()
        val ssb = SpannableStringBuilder()
        val ssTop = SpannableString(spanStr)

        val spanFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        ssTop.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.color_000)),
            0,
            ssTop.length,
            spanFlag
        )
        ssTop.setSpan(AbsoluteSizeSpan(18, true), 0, ssTop.length, spanFlag)
        ssTop.setSpan(StyleSpan(Typeface.BOLD), 0, ssTop.length, spanFlag)

        ssb.append(ssTop)
        ssb.append(bottomText)

        return ssb
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.requestProfile()
        }
    }

}