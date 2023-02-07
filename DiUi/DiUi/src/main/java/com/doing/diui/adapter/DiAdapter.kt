package com.doing.diui.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.containsKey
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.lang.reflect.ParameterizedType

class DiAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val dataList =
        mutableListOf<DiHolderItem<*, out ViewHolder>>()
    private val typeMaps = SparseArray<DiHolderItem<*, out ViewHolder>>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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
        val item = dataList[position]
        item.onBindView(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        val item = dataList[position]
        val type = item.javaClass.hashCode()
        if (!typeMaps.containsKey(type)) {
            typeMaps.put(type, item)
        }
        return type
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @JvmOverloads
    fun addHolderItem(index: Int = dataList.size, item: DiHolderItem<*, out ViewHolder>) {
        var target = index
        if (index < 0) {
            target = dataList.size
        }

        dataList.add(target, item)
        notifyItemInserted(target)
    }

    fun addHolderItems(items: List<DiHolderItem<*, out ViewHolder>>) {
        val start = items.size
        dataList.addAll(items)

        notifyItemRangeInserted(start, items.size)
    }

    fun removeItem(index: Int): DiHolderItem<*, out ViewHolder>? {
        if (index > 0 && index < dataList.size) {
            val remove = dataList.removeAt(index)
            notifyItemRemoved(index)
            return remove
        }
        return null
    }

    fun removeItem(item: DiHolderItem<*, out ViewHolder>): Int {
        val index = dataList.indexOf(item)
        if (index != -1) {
            dataList.removeAt(index)
            notifyItemRemoved(index)
        }
        return index
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataList.size) {
                        val itemSpan = dataList[position].getSpanCount()
                        return if (itemSpan <= 0) spanCount else itemSpan
                    }
                    return spanCount
                }
            }
        }
    }

    fun refreshItem(item: DiHolderItem<*, out ViewHolder>) {
        val index = dataList.indexOf(item)
        if (index >= 0) {
            notifyItemChanged(index)
        }
    }

}