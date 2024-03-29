package com.nchung.sample.recyclerintercept

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_parent.layoutManager = LinearLayoutManager(this)
        recycler_parent.adapter = ParentAdapter(
            DataCreator(),
            onParentClick = {
                val adapter = recycler_parent.adapter as ParentAdapter
                it.isCheckAll = !it.isCheckAll
                adapter.setCheckedAll(it)
                Log.e("Click", "${adapter.countItemsChecked()}")
            },
            onChildClick = {
                val adapter = recycler_parent.adapter as ParentAdapter
                adapter.setChecked(it)
                Log.e("Click", "${adapter.countItemsChecked()}")
            }
        )
        (recycler_parent.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }
}
