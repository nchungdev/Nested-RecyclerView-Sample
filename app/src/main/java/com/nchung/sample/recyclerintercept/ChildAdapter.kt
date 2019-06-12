package com.nchung.sample.recyclerintercept

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView

class ChildAdapter(private val list: List<Child>, private val onClick: (Child) -> Unit) : RecyclerView.Adapter<ChildAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
        val params = view.layoutParams as RecyclerView.LayoutParams
        params.width = (parent.measuredWidth - getTotalMargin(parent.resources)) / 4
        view.layoutParams = params
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position], onClick)
    }

    private fun getTotalMargin(resources: Resources): Int {
        val margin4 = resources.getDimensionPixelSize(R.dimen.margin4)
        val margin16 = resources.getDimensionPixelSize(R.dimen.margin16)
        return margin16 * 2 + 3 * margin4 * 2
    }

    fun setChecked(child: Child) {
        notifyItemChanged(list.indexOf(child))
    }

    fun isCheckedAll() = list.all { it.isChecked }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ckbName = itemView.findViewById<CheckBox>(R.id.ckb_selection)
        private val imageView = itemView.findViewById<ImageView>(R.id.image_icon)
        private val margin4 = itemView.resources.getDimensionPixelSize(R.dimen.margin4)
        private val margin16 = itemView.resources.getDimensionPixelSize(R.dimen.margin16)

        fun bindData(child: Child, onClick: (Child) -> Unit) {
            ckbName.isChecked = child.isChecked
            imageView.setImageResource(child.icon)
            val params = itemView.layoutParams as RecyclerView.LayoutParams
            params.marginStart = if (adapterPosition == 0) margin16 else margin4
            params.marginEnd = if (adapterPosition == itemCount - 1) margin16 else margin4
            itemView.layoutParams = params
            itemView.setOnClickListener { onClick(child) }
        }
    }
}