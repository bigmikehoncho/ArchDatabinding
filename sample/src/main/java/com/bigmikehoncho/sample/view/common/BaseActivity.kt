package com.bigmikehoncho.sample.view.common

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bigmikehoncho.mvvmdatabinding.MvvmBinder
import com.bigmikehoncho.mvvmdatabinding.VMBinder
import com.bigmikehoncho.sample.view.bindings.BindingAdapters

abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity(), VMBinder {

    private val mvvmBinder = MvvmBinder<Binding>(BindingAdapters.defaultBinder)
    protected lateinit var binding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.addObserver(mvvmBinder)

        componentInjection()

        super.onCreate(savedInstanceState)

        binding = mvvmBinder.createBinding(this)

        onInitFields()

        if (savedInstanceState == null) {
            onStartFromNewState()
        } else {
            onRestoreFromSavedState(savedInstanceState)
        }

        onImplFields()
    }

    protected abstract fun componentInjection()

    protected open fun onInitFields() {}

    protected open fun onStartFromNewState() {}

    protected open fun onRestoreFromSavedState(savedInstanceState: Bundle) {}

    protected open fun onImplFields() {}
}
