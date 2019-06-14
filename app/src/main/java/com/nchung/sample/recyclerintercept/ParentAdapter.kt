package com.nchung.sample.recyclerintercept

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout

class ParentAdapter(
    private val parents: List<Parent>,
    private val onParentClick: (Parent) -> Unit,
    private val onChildClick: (Parent) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        0 -> HeaderHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false))
        else -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_parent, parent, false))
    }

    override fun getItemCount() = parents.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) holder.bindData(parents[position - 1])
    }

    override fun getItemViewType(position: Int) = if (position == 0) 0 else 1

    fun setCheckedAll(parent: Parent) {
        parent.childs.map { it.isChecked = parent.isCheckAll }
        setChecked(parent)
    }

    fun setChecked(parent: Parent) = notifyItemChanged(parents.indexOf(parent) + 1)

    fun getItemsChecked() = parents.map(Parent::getChildChecked).flatten()

    fun countItemsChecked() = getItemsChecked().size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = itemView.findViewById<FrameLayout>(R.id.name_container)
        private val checkBox = itemView.findViewById<CheckBox>(R.id.ckb_all)
        private val recyclerView = itemView.findViewById<RecyclerView>(R.id.recycler_child)

        fun bindData(parent: Parent) {
            checkBox.text = parent.name
            checkBox.isChecked = parent.isCheckAll
            container.setOnClickListener {
                onParentClick(parent)
            }
            recyclerView.layoutManager = GridLayoutManager(itemView.context, 1, RecyclerView.HORIZONTAL, false)
            recyclerView.adapter = ChildAdapter(parent.childs) { child ->
                val adapter = recyclerView.adapter as ChildAdapter
                child.isChecked = !child.isChecked
                adapter.setChecked(child)
                parent.isCheckAll = adapter.isCheckedAll()
                onChildClick(parent)
            }
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    inner class HeaderHolder(view: View) : RecyclerView.ViewHolder(view)
}
