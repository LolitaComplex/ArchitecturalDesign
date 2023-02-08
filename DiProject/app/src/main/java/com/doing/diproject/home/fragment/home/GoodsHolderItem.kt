package com.doing.diproject.home.fragment.home

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doing.diproject.R
import com.doing.diproject.home.model.HomeList
import com.doing.diui.adapter.DiHolderItem
import com.doing.diui.adapter.DiViewHolder
import com.doing.hilibrary.util.DiDisplayUtil
import kotlinx.android.synthetic.main.layout_home_goods_list_item1.view.*

class GoodsHolderItem(private val goodsModel: HomeList.GoodsList,
                      private val isHotTab: Boolean)
    : DiHolderItem<HomeList.GoodsList, DiViewHolder>(goodsModel) {

    companion object {
         const val MAX_TAG_SIZE = 3
    }


    override fun onBindView(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        val imageView = holder.itemView.item_image
        Glide.with(imageView.context).load(goodsModel.sliderImage).into(imageView)
        holder.itemView.item_title.text = goodsModel.goodsName

        holder.itemView.item_price.text = goodsModel.marketPrice
        holder.itemView.item_sale_desc.text = goodsModel.completedNumText


        val itemLabelContainer = holder.itemView.item_label_container
        if (!TextUtils.isEmpty(goodsModel.tags)) {
            itemLabelContainer.visibility = View.VISIBLE
            val split = goodsModel.tags.split(" ")
            for (index in split.indices) { //0...split.size-1
                //0  ---3
                val childCount = itemLabelContainer.childCount
                if (index > MAX_TAG_SIZE - 1) {
                    //倒叙
                    for (index in childCount - 1 downTo MAX_TAG_SIZE - 1) {
                        // itemLabelContainer childcount =5
                        // 3，后面的两个都需要被删除
                        itemLabelContainer.removeViewAt(index)
                    }
                    break
                }
                //这里有个问题，有着一个服用的问题   5 ,4
                //解决上下滑动复用的问题--重复创建的问题
                val labelView: TextView = if (index > childCount - 1) {
                    val view = createLabelView(context, index != 0)
                    itemLabelContainer.addView(view)
                    view
                } else {
                    itemLabelContainer.getChildAt(index) as TextView
                }
                labelView.text = split[index]
            }
        } else {
            itemLabelContainer.visibility = View.GONE
        }

        if (!isHotTab) {
            val margin = DiDisplayUtil.dp2px(2f)
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            val parentLeft = getAdapter().getAttachRecyclerView()?.left ?: 0
            val parentPaddingLeft = getAdapter().getAttachRecyclerView()?.paddingLeft ?: 0
            val itemLeft = holder.itemView.left
            if (itemLeft == (parentLeft + parentPaddingLeft)) {
                params.rightMargin = margin
            } else {
                params.leftMargin = margin
            }
            holder.itemView.layoutParams = params
        }
    }

    private fun createLabelView(context: Context, withLeftMargin: Boolean): TextView {
        val labelView = TextView(context)
        labelView.setTextColor(ContextCompat.getColor(context, R.color.color_e75))
        labelView.setBackgroundResource(R.drawable.shape_goods_label)
        labelView.textSize = 11f
        labelView.gravity = Gravity.CENTER
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            DiDisplayUtil.dp2px(16f)
        )
        params.leftMargin = if (withLeftMargin) DiDisplayUtil.dp2px(5f) else 0
        labelView.layoutParams = params
        return labelView
    }

    override fun getLayoutId(): Int {
        return if (isHotTab) R.layout.layout_home_goods_list_item1 else R.layout.layout_home_goods_list_item2
    }

    override fun getSpanSize(): Int {
        return if (isHotTab) super.getSpanSize() else 1
    }
}