package com.bigmikehoncho.mvvmdatabinding

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bigmikehoncho.mvvmdatabinding.utils.BindingUtils

class MvvmBinder<out Binding : ViewDataBinding> : LifecycleObserver {

    private lateinit var binding: Binding
    private val defaultBinder = BindingUtils.defaultBinder

    /**
     * Call after super.createBinding of activity
     *
     * @param activity as VMBinder
     */
    fun createBinding(activity: AppCompatActivity): Binding {
        activity as VMBinder
        binding = DataBindingUtil.setContentView(activity, activity.layoutId)
        defaultBinder.bind(binding, ViewModelProviders.of(activity).get(activity.provideViewModelClass()))
        return binding
    }

    fun createBinding(fragment: android.support.v4.app.Fragment): Binding {
        fragment as VMBinder
        binding = DataBindingUtil.inflate(fragment.layoutInflater, fragment.layoutId, null, false)
        defaultBinder.bind(binding, ViewModelProviders.of(fragment).get(fragment.provideViewModelClass()))
        return binding
    }

    /**
     * Call before super.onDestroy or super.onDestroyView
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        Log.i("MvvmBinder", "onDestroy")
        defaultBinder.bind(binding, null)
        binding.executePendingBindings()
    }
}
