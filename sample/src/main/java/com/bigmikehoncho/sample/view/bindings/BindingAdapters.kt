package com.bigmikehoncho.sample.view.bindings

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bigmikehoncho.mvvmdatabinding.adapters.ViewModelBinder
import com.bigmikehoncho.mvvmdatabinding.adapters.ViewProvider
import com.bigmikehoncho.mvvmdatabinding.utils.bindAdapterWithDefaultBinder
import com.bigmikehoncho.sample.BR
import com.bigmikehoncho.sample.SampleApp

object BindingAdapters {

    @NonNull
    val defaultBinder: ViewModelBinder = object : ViewModelBinder {
        override fun bind(viewDataBinding: ViewDataBinding, viewModel: ViewModel?) {
            viewDataBinding.setVariable(BR.vm, viewModel)
        }
    }

    @BindingAdapter("android:visibility")
    fun bindVisibility(@NonNull view: View, @Nullable visible: Boolean?) {
        val visibility = if (visible != null && visible) View.VISIBLE else View.GONE
        view.visibility = visibility
    }


    /**
     * Binding Adapter Wrapper for checking memory leak
     */
    @BindingAdapter("items", "view_provider")
    fun <VM : ViewModel> bindRecyclerViewAdapter(recyclerView: RecyclerView, items: ObservableList<VM>, viewProvider: ViewProvider) {
        val previousAdapter = recyclerView.adapter
        bindAdapterWithDefaultBinder(recyclerView, items, viewProvider)

        // Previous adapter should get deallocated
        if (previousAdapter != null)
            SampleApp.refWatcher.watch(previousAdapter)
    }

    /**
     * Binding Adapter Wrapper for checking memory leak
     */
    @BindingAdapter("items", "view_provider")
    fun <VM : ViewModel> bindViewPagerAdapter(viewPager: ViewPager, items: ObservableList<VM>, viewProvider: ViewProvider) {
        val previousAdapter = viewPager.adapter
        bindAdapterWithDefaultBinder(viewPager, items, viewProvider)

        // Previous adapter should get deallocated
        if (previousAdapter != null)
            SampleApp.refWatcher.watch(previousAdapter)
    }
}