package com.doing.diproject.home.fragment.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doing.diproject.R
import com.doing.diproject.home.model.HomeList
import com.doing.diproject.home.model.HomeList.SubcategoryList
import com.doing.diui.adapter.DiHolderItem
import com.doing.diui.adapter.DiViewHolder
import com.doing.hilibrary.util.DiDisplayUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_home_op_grid_item.*
import kotlinx.android.synthetic.main.layout_home_op_grid_item.view.item_image
import kotlinx.android.synthetic.main.layout_home_op_grid_item.view.item_title

class GridHolderItem(private val bannerList: List<SubcategoryList>)
    : DiHolderItem<List<SubcategoryList>, DiViewHolder>(bannerList) {

    override fun getLayoutView(container: ViewGroup): View {
        return RecyclerView(container.context).apply {
            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            params.bottomMargin = DiDisplayUtil.dp2px(10f)
            this.layoutManager = GridLayoutManager(container.context, 5)
            this.layoutParams = params
            this.setBackgroundColor(Color.WHITE)
        }
    }

    override fun onBindView(holder: RecyclerView.ViewHolder, position: Int) {
        val context = holder.itemView.context
        val gridView = holder.itemView as RecyclerView
        gridView.adapter = GridAdapter(context, bannerList)
    }

    inner class GridAdapter(val context: Context, val list: List<SubcategoryList>) :
        RecyclerView.Adapter<HMyViewHolder>() {
        private var inflater = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HMyViewHolder {
            val view = inflater.inflate(R.layout.layout_home_op_grid_item, parent, false)
            return HMyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: HMyViewHolder, position: Int) {
            val subcategory = list[position]

//            holder.item_image.loadUrl(subcategory.subcategoryIcon)
//            holder.item_title.text = subcategory.subcategoryName
            val imageView = holder.itemView.item_image
            Glide.with(imageView.context).load(subcategory.subcategoryIcon).into(imageView)
            holder.itemView.item_title.text = subcategory.subcategoryName

            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("categoryId", subcategory.categoryId)
                bundle.putString("subcategoryId", subcategory.subcategoryId)
                bundle.putString("categoryTitle", subcategory.subcategoryName)
//                HiRoute.startActivity(context, bundle, GOODS_LIST)
            }
        }
    }

    class HMyViewHolder(val view: View) : RecyclerView.ViewHolder(view)/*, LayoutContainer */{
//        override val containerView: View
//            get() = view
    }

}