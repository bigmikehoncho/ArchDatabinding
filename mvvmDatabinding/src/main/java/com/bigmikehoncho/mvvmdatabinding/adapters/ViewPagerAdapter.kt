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

package com.bigmikehoncho.mvvmdatabinding.adapters

import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class ViewPagerAdapter<VM : ViewModel>(private val source: ObservableList<VM>, private val viewProvider: ViewProvider, private val binder: ViewModelBinder) : PagerAdapter(), Connectable {

    private val onListChangedCallback = object : ObservableList.OnListChangedCallback<ObservableList<VM>>() {
        override fun onChanged(viewModels: ObservableList<VM>) {
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(viewModels: ObservableList<VM>, start: Int, count: Int) {
            notifyDataSetChanged()
        }

        override fun onItemRangeInserted(viewModels: ObservableList<VM>, start: Int, count: Int) {
            notifyDataSetChanged()
        }

        override fun onItemRangeMoved(viewModels: ObservableList<VM>, from: Int, to: Int, count: Int) {
            notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(viewModels: ObservableList<VM>, start: Int, count: Int) {
            notifyDataSetChanged()
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val vm = source[position]
        val layoutId = viewProvider.getView(vm)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(container.context),
                layoutId,
                container,
                false)
        binder.bind(binding, vm)
        binding.root.tag = vm
        container.addView(binding.root)
        return binding
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val binding = `object` as ViewDataBinding
        binder.bind(binding, null)
        binding.executePendingBindings()
        container.removeView(binding.root)
    }

    override fun getItemPosition(`object`: Any?): Int {
        return super.getItemPosition(`object`)
    }

    override fun getCount(): Int {
        return source.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (`object` as ViewDataBinding).root === view
    }

    override fun connect() {
        source.addOnListChangedCallback(onListChangedCallback)
    }

    override fun removeCallback() {
        source.removeOnListChangedCallback(onListChangedCallback)
    }
}
