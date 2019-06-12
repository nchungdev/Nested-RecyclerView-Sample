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
    private val onClick: (Parent) -> Unit,
    private val onChildClick: (Parent) -> Unit
) : RecyclerView.Adapter<ParentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_parent, parent, false))

    override fun getItemCount() = parents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(parents[position], onClick, onChildClick)
    }

    fun setCheckedAll(parent: Parent) {
        parent.childs.map { it.isChecked = parent.isCheckAll }
        notifyItemChanged(parents.indexOf(parent))
    }

    fun setChecked(parent: Parent) {
        notifyItemChanged(parents.indexOf(parent))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val container = itemView.findViewById<FrameLayout>(R.id.name_container)
        private val checkBox = itemView.findViewById<CheckBox>(R.id.ckb_all)
        private val recyclerView = itemView.findViewById<RecyclerView>(R.id.recycler_child)

        fun bindData(parent: Parent, onClick: (Parent) -> Unit, onChildClick: (Parent) -> Unit) {
            checkBox.text = parent.name
            checkBox.isChecked = parent.isCheckAll
            recyclerView.layoutManager = GridLayoutManager(itemView.context, 1, RecyclerView.HORIZONTAL, false)
            recyclerView.adapter = ChildAdapter(parent.childs) { child ->
                child.isChecked = !child.isChecked
                val adapter = recyclerView.adapter as ChildAdapter
                adapter.setChecked(child)
                parent.isCheckAll = adapter.isCheckedAll()
                onChildClick(parent)
            }
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            container.setOnClickListener {
                onClick(parent)
            }
        }
    }
}
