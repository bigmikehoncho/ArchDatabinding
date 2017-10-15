package com.bigmikehoncho.sample.view.common

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bigmikehoncho.mvvmdatabinding.MvvmBinder
import com.bigmikehoncho.mvvmdatabinding.VMBinder

abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity(), VMBinder {

    protected val mvvmBinder = MvvmBinder<Binding>()
    protected lateinit var binding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = mvvmBinder.onCreate(this)

        onInitFields()

        if (savedInstanceState == null) {
            onStartFromNewState()
        } else {
            onRestoreFromSavedState(savedInstanceState)
        }

        onImplFields()
    }

    protected open fun onInitFields() {}

    protected open fun onStartFromNewState() {}

    protected open fun onRestoreFromSavedState(savedInstanceState: Bundle) {}

    protected open fun onImplFields() {}
}
