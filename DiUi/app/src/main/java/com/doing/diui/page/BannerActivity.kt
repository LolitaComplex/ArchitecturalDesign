package com.doing.diui.page

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.doing.diui.R
import com.doing.diui.banner.DiBanner
import com.doing.diui.banner.core.DiBannerModel
import com.doing.diui.banner.core.DiViewPagerAdapter.*

class BannerActivity : AppCompatActivity() {

    private val urls = listOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner)

        val banner = findViewById<DiBanner<DiBannerModel>>(R.id.BannerActivity_banner)
        banner.setAutoPlay(true)
        banner.setLoop(true)
        banner.setIntervalTime(5000)
        banner.setBindAdapter(object :  IDiBannerBindAdapter<DiBannerModel> {

            override fun onBind(holder: DiViewPagerItemHolder, model: DiBannerModel, position: Int) {
                val image = holder.find<ImageView>(R.id.LayoutBannerItem_iv_img)
                when (val type = model.type) {
                    is DiBannerModel.Type.Url -> {
                        Glide.with(image).load(type.url).transform(CenterCrop()).into(image)
                    }
                    is DiBannerModel.Type.File -> {
                        Glide.with(image).load(type.dir).transform(CenterCrop()).into(image)
                    }
                    is DiBannerModel.Type.Resource -> {
                        Glide.with(image).load(type.id).transform(CenterCrop()).into(image)
                    }
                }
            }
        })
        banner.setOnBannerCLickListener(object :  IDiBannerClickListener<DiBannerModel> {

            override fun onClick(holder: DiViewPagerItemHolder, model: DiBannerModel, position: Int) {
                Toast.makeText(this@BannerActivity,
                    "点击了Banner:${position}", Toast.LENGTH_LONG).show()
            }
        })
        banner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float,
                        positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                Log.d("Doing", "当前页码: $position")
//                Toast.makeText(this@BannerActivity,
//                    "到达了第${position}页 ", Toast.LENGTH_LONG).show()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        val models: List<DiBannerModel> = urls.map { url ->
            DiBannerModel(DiBannerModel.Type.Url(url))
        }
        banner.setBannerData(R.layout.layout_banner_item_default, models)
    }
}