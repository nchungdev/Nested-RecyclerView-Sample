package com.nchung.sample.recyclerintercept

data class Parent(val name: String, val childs: List<Child>, var isCheckAll: Boolean = false) {
    fun getChildChecked() = childs.filter { it.isChecked }
}
