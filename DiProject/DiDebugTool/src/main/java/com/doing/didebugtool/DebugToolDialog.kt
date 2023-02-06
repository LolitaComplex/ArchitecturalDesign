package com.doing.didebugtool

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doing.hilibrary.util.DiDisplayUtil
import kotlinx.android.extensions.LayoutContainer
import java.lang.reflect.Method
import kotlinx.android.synthetic.main.di_debug_tool.*
import kotlinx.android.synthetic.main.di_debug_tool_item.*

class DebugToolDialog : AppCompatDialogFragment() {

    private lateinit var appContext: Context
    private val debugTools = arrayOf(DebugTool::class.java)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.appContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val context = context ?: return null
        val with = (DiDisplayUtil.getScreenWidthInPx(context) * 0.7f).toInt()
        val container = FrameLayout(context).apply {
            this.layoutParams = ViewGroup.LayoutParams(
                with, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        inflater.inflate(R.layout.di_debug_tool, container, true)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_di_debug_tool)
        return container
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemDecoration = DividerItemDecoration(view.context,
            DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(view.context,
            R.drawable.shape_di_debug_tool_divider)!!)

        val debugList = mutableListOf<DebugFunction>()
        debugTools.forEach { clazz ->
            val instance = clazz.getConstructor().newInstance()
            val methods = instance.javaClass.declaredMethods
            for (method in methods) {
                var title = ""
                var desc = ""
                var enable = false
                val annotation = method.getAnnotation(DiDebug::class.java)
                if (annotation != null) {
                    title = annotation.name
                    desc = annotation.desc
                    enable = true
                } else {
                    method.isAccessible = true
                    title = method.invoke(instance) as String
                }

                debugList.add(DebugFunction(title,desc, method, enable, instance))
            }
        }

        debugRecyclerView.addItemDecoration(itemDecoration)
        debugRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        debugRecyclerView.adapter = DebugAdapter(debugList)
    }

    class DebugAdapter(private val debugList: List<DebugFunction>)
            : RecyclerView.Adapter<DebugAdapter.DebugViewHolder>() {

        inner class DebugViewHolder(private val view: View) :
                RecyclerView.ViewHolder(view), LayoutContainer {
            override val containerView: View
                get() = view
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebugViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.di_debug_tool_item,
                parent, false
            )

            val holder = DebugViewHolder(itemView)
            itemView.setOnClickListener {
                val debug = debugList[holder.adapterPosition]
                    if (debug.enable) {
                        debug.method.invoke(debug.target)
                }
            }

            return holder
        }

        override fun onBindViewHolder(holder: DebugViewHolder, position: Int) {
            val data = debugList[position]
            holder.item_title.text = data.name
            holder.item_desc.text = data.desc
        }

        override fun getItemCount(): Int {
            return debugList.size
        }
    }

    data class DebugFunction(
        val name: String,
        val desc: String,
        val method: Method,
        val enable: Boolean,
        val target: Any
    )
}