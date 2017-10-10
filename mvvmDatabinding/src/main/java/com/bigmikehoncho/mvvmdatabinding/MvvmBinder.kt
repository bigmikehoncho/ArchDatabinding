package com.bigmikehoncho.mvvmdatabinding

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater

import com.bigmikehoncho.mvvmdatabinding.adapters.ViewModelBinder
import com.bigmikehoncho.mvvmdatabinding.utils.BindingUtils

/**
 * Created by mike on 3/3/17.
 */

class MvvmBinder<Binding : ViewDataBinding> {
    private lateinit var binding: Binding

    private val defaultBinder: ViewModelBinder
        get() {
            return BindingUtils.defaultBinder
        }

    /**
     * Call after super.onCreate of activity
     *
     * @param activity as VMBinder
     */
    fun onCreate(activity: Activity): Binding? {
        val binder = activity as VMBinder
        binding = DataBindingUtil.setContentView(activity, binder.layoutId)
        defaultBinder.bind(binding, binder.createViewModel())
        return binding
    }

    fun onCreate(binder: VMBinder, inflater: LayoutInflater): Binding? {
        binding = DataBindingUtil.inflate(inflater, binder.layoutId, null, false)
        defaultBinder.bind(binding, binder.createViewModel())
        return binding
    }

    /**
     * Call before super.onDestroy or super.onDestroyView
     */
    fun onDestroy() {
        defaultBinder.bind(binding, null)
        binding.executePendingBindings()
    }
}
