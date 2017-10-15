package com.bigmikehoncho.mvvmdatabinding

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import com.bigmikehoncho.mvvmdatabinding.utils.BindingUtils

class MvvmBinder<out Binding : ViewDataBinding> {
    private lateinit var binding: Binding

    private val defaultBinder = BindingUtils.defaultBinder

    /**
     * Call after super.onCreate of activity
     *
     * @param activity as VMBinder
     */
    fun onCreate(activity: AppCompatActivity): Binding {
        activity as VMBinder
        binding = DataBindingUtil.setContentView(activity, activity.layoutId)
        defaultBinder.bind(binding, ViewModelProviders.of(activity).get(activity.provideViewModelClass()))
        return binding
    }

    fun onCreate(fragment: android.support.v4.app.Fragment): Binding {
        fragment as VMBinder
        binding = DataBindingUtil.inflate(fragment.layoutInflater, fragment.layoutId, null, false)
        defaultBinder.bind(binding, ViewModelProviders.of(fragment).get(fragment.provideViewModelClass()))
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
