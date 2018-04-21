package com.bigmikehoncho.mvvmdatabinding.utils

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bigmikehoncho.mvvmdatabinding.adapters.Connectable
import com.bigmikehoncho.mvvmdatabinding.adapters.RecyclerViewAdapter
import com.bigmikehoncho.mvvmdatabinding.adapters.ViewPagerAdapter
import com.bigmikehoncho.mvvmdatabinding.adapters.ViewProvider


@BindingAdapter("adapter")
fun bindAdapter(viewPager: ViewPager, adapter: PagerAdapter?) {
    val oldAdapter = viewPager.adapter

    // Disconnect previous adapter if its Connectable
    if (oldAdapter != null && oldAdapter is Connectable) {
        (oldAdapter as Connectable).removeCallback()
    }

    // Connect the new adapter
    if (adapter != null && adapter is Connectable) {
        (adapter as Connectable).connect()
    }
    viewPager.adapter = adapter
}

@BindingAdapter("adapter")
fun bindAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    recyclerView.adapter = adapter
}

@BindingAdapter("items", "view_provider")
fun <VM : ViewModel> bindAdapterWithDefaultBinder(recyclerView: RecyclerView, items: ObservableList<VM>?, viewProvider: ViewProvider?) {
    var adapter: RecyclerViewAdapter<*>? = null
    if (items != null && viewProvider != null) {
        if (recyclerView.adapter != null) {
            return
        }
        adapter = RecyclerViewAdapter(items, viewProvider, BindingUtils.defaultBinder)
    }
    bindAdapter(recyclerView, adapter)
}

@BindingAdapter("items", "view_provider")
fun <VM : ViewModel> bindAdapterWithDefaultBinder(viewPager: ViewPager, items: ObservableList<VM>?, viewProvider: ViewProvider?) {
    var adapter: ViewPagerAdapter<*>? = null
    if (items != null && viewProvider != null) {
        if (viewPager.adapter != null) {
            return
        }
        adapter = ViewPagerAdapter(items, viewProvider, BindingUtils.defaultBinder)
    }
    bindAdapter(viewPager, adapter)
}

@BindingAdapter(value = ["layout_vertical", "reverse_layout"], requireAll = false)
fun bindLayoutManager(recyclerView: RecyclerView, vertical: Boolean, reverseLayout: Boolean) {
    val orientation = if (vertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, orientation, reverseLayout)
}