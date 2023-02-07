package com.doing.diui.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.containsKey
import androidx.core.util.containsValue
import androidx.core.util.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

class DiAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val dataList =
        mutableListOf<DiHolderItem<*, out ViewHolder>>()
    private val typeMaps = SparseArray<DiHolderItem<*, out ViewHolder>>()
    private var weakRecyclerView: WeakReference<RecyclerView>? = null


    private val headers = SparseArray<View>()
    private val footers = SparseArray<View>()

    private var BASE_HEADER_VIEW_TYPE = 100000
    private var BASE_FOOTER_VIEW_TYPE = 200000

    fun addHeader(view: View) {
        if (!headers.containsValue(view)) {
            headers.put(BASE_HEADER_VIEW_TYPE++, view)
            notifyItemInserted(headers.size() - 1)
        }
    }

    fun removeHeader(view: View) {
        val index = headers.indexOfValue(view)
        if (index >= 0) {
            headers.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun addFooter(view: View) {
        if (!footers.containsValue(view)) {
            footers.put(BASE_FOOTER_VIEW_TYPE++, view)
            notifyItemInserted(itemCount)
        }
    }

    fun removeFooter(view: View) {
        val index = footers.indexOfValue(view)
        if (index >= 0) {
            footers.removeAt(index)
            notifyItemRemoved(headers.size() + dataList.size + index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (headers.containsKey(viewType)) {
            return object : ViewHolder(headers[viewType]) {}
        }

        if (footers.containsKey(viewType)) {
            return object : ViewHolder(footers[viewType]) {}
        }

        val item = typeMaps.get(viewType)
        val view = item.getLayoutView(parent)
        return createViewHolderInternal(item.javaClass, view)
    }

    private fun createViewHolderInternal(clazz: Class<DiHolderItem<*, out ViewHolder>>, view: View): ViewHolder {
        val superclass = clazz.genericSuperclass
        if (superclass is ParameterizedType) {
            val arguments = superclass.actualTypeArguments
            arguments.forEach { generic ->
                if (generic is Class<*> &&
                        ViewHolder::class.java.isAssignableFrom(generic)) {
                    return generic.getConstructor(View::class.java).newInstance(view) as ViewHolder
                }
            }
        }

        return object : ViewHolder(view) {}
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }

        val item = getItem(position - headers.size)
        item!!.onBindView(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        if (isHeaderPosition(position)) {
            return headers.keyAt(position) // SpareArray排序好了，所以是有序的，这样就能取出来我们插入的第一个
        }

        if (isFooterPosition(position)) {
            val footerPos = position - headers.size - dataList.size
            return footers.keyAt(footerPos)
        }

        val item = getItem(position - headers.size)
        val type = item!!.javaClass.hashCode()
        if (!typeMaps.containsKey(type)) {
            typeMaps.put(type, item)
        }
        return type
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < headers.size
    }

    private fun isFooterPosition(position: Int): Boolean {
        return position >= headers.size + dataList.size
    }

    override fun getItemCount(): Int {
        return dataList.size + headers.size + footers.size
    }

    @JvmOverloads
    fun addHolderItem(index: Int = dataList.size, item: DiHolderItem<*, out ViewHolder>) {
        var target = index
        if (index < 0) {
            target = dataList.size
        }

        dataList.add(target, item)
        notifyItemInserted(target + headers.size())
    }

    fun addHolderItems(items: List<DiHolderItem<*, out ViewHolder>>) {
        val start = items.size + headers.size
        dataList.addAll(items)

        notifyItemRangeInserted(start, items.size)
    }

    fun removeItem(index: Int): DiHolderItem<*, out ViewHolder>? {
        if (index > 0 && index < dataList.size) {
            val remove = dataList.removeAt(index)
            notifyItemRemoved(index + headers.size())
            return remove
        }
        return null
    }

    fun removeItem(item: DiHolderItem<*, out ViewHolder>): Int {
        val index = dataList.indexOf(item)
        if (index != -1) {
            dataList.removeAt(index)
            notifyItemRemoved(index + headers.size())
        }
        return index
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        weakRecyclerView = WeakReference(recyclerView)

        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isFooterPosition(position) || isHeaderPosition(position)) {
                        return spanCount
                    }

                    val targetPos = position - headers.size
                    if (targetPos < dataList.size) {
                        val itemSpan = getItem(targetPos)!!.getSpanCount()
                        return if (itemSpan <= 0) spanCount else itemSpan
                    }
                    return spanCount
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

        val recyclerView = getAttachRecyclerView() ?: return
        val position = recyclerView.getChildAdapterPosition(holder.itemView)
        if (isHeaderPosition(position)) {
            return
        }

        val itemPosition = position - headers.size
        if (itemPosition < dataList.size) {
            val item = getItem(itemPosition)
            val params = holder.itemView.layoutParams
            if (item != null && params != null &&
                params is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager
                        as StaggeredGridLayoutManager

                if (isHeaderPosition(position) || isFooterPosition(position)) {
                    params.isFullSpan = true
                    return
                }

                val spanSize = item.getSpanSize()
                if (spanSize == manager.spanCount) {
                    params.isFullSpan = true
                }
            }
        }
    }

    fun getItem(position: Int): DiHolderItem<*, out ViewHolder>? {
        if (position <= dataList.size) {
            return dataList[position]
        }

        return null
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        weakRecyclerView?.clear()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

    }

    open fun getAttachRecyclerView(): RecyclerView? {
        return weakRecyclerView?.get()
    }


    fun refreshItem(item: DiHolderItem<*, out ViewHolder>) {
        val index = dataList.indexOf(item)
        if (index >= 0) {
            notifyItemChanged(index + headers.size())
        }
    }

}