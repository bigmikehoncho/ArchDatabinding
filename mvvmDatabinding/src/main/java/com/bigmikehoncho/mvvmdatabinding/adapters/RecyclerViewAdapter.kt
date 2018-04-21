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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

open class RecyclerViewAdapter<VM : ViewModel>(private val source: ObservableList<VM>,
                                          private val viewProvider: ViewProvider,
                                          private val binder: ViewModelBinder) : RecyclerView.Adapter<RecyclerViewAdapter.DataBindingViewHolder>() {
    private val onListChangedCallback = object : ObservableList.OnListChangedCallback<ObservableList<VM>>() {
        override fun onChanged(viewModels: ObservableList<VM>) {
            notifyDataSetChanged()
        }

        override fun onItemRangeChanged(viewModels: ObservableList<VM>, start: Int, count: Int) {
            notifyItemRangeChanged(start, count)
        }

        override fun onItemRangeInserted(viewModels: ObservableList<VM>, start: Int, count: Int) {
            notifyItemRangeInserted(start, count)
        }

        override fun onItemRangeMoved(viewModels: ObservableList<VM>, from: Int, to: Int, count: Int) {
            notifyItemRangeRemoved(from, count)
            notifyItemRangeInserted(to, count)
        }

        override fun onItemRangeRemoved(viewModels: ObservableList<VM>, start: Int, count: Int) {
            notifyItemRangeRemoved(start, count)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewProvider.getView(source[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        val vm = source[position]
        holder.viewBinding.root.tag = vm
        binder.bind(holder.viewBinding, vm)
        holder.viewBinding.executePendingBindings()
    }

    override fun onViewRecycled(holder: DataBindingViewHolder) {
        binder.bind(holder.viewBinding, null)
        holder.viewBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return source.size
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        source.addOnListChangedCallback(onListChangedCallback)
        super.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)

        source.removeOnListChangedCallback(onListChangedCallback)
    }

    class DataBindingViewHolder(internal val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root)
}
