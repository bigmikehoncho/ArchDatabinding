/*
 * Copyright 2016 Manas Chaudhari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bigmikehoncho.mvvmdatabinding.utils

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.annotation.LayoutRes
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bigmikehoncho.mvvmdatabinding.adapters.*
import io.reactivex.Observable
import java.util.*

object BindingUtils {

    lateinit var defaultBinder: ViewModelBinder

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
            adapter = RecyclerViewAdapter(items, viewProvider, defaultBinder)
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
            adapter = ViewPagerAdapter(items, viewProvider, defaultBinder)
        }
        bindAdapter(viewPager, adapter)
    }

    @BindingConversion
    fun getViewProviderForStaticLayout(@LayoutRes layoutId: Int): ViewProvider {
        return object : ViewProvider {
            override fun getView(vm: ViewModel): Int {
                return layoutId
            }
        }
    }

    @BindingConversion
    fun <VM : ViewModel> toGenericList(specificList: Observable<List<VM>>?): Observable<List<VM>>? {
        return specificList?.map { ts -> ArrayList(ts) }
    }

    @BindingConversion
    fun <VM : ViewModel> toObservableList(specificList: List<VM>?): ObservableList<VM>? {
        return if (specificList == null) {
            null
        } else {
            val observableList = ObservableArrayList<VM>()
            observableList.addAll(specificList)
            observableList
        }
    }

    // Extra Utilities

    @BindingAdapter(value = *arrayOf("layout_vertical", "reverse_layout"), requireAll = false)
    fun bindLayoutManager(recyclerView: RecyclerView, vertical: Boolean, reverseLayout: Boolean) {
        val orientation = if (vertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, orientation, reverseLayout)
    }
}
