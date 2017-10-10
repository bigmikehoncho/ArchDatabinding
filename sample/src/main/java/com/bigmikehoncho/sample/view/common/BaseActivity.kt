package com.bigmikehoncho.sample.view.common

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by mike on 10/5/17.
 */
abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {

    protected var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(provideViewModelClass())
        super.onCreate(savedInstanceState)

        onInitFields()

        if (savedInstanceState == null) {
            onStartFromNewState()
        } else {
            onRestoreFromSavedState(savedInstanceState)
        }

        onImplFields()
    }

    protected abstract fun provideLayoutId(): Int

    protected abstract fun provideViewModelClass(): Class<VM>

    protected fun onInitFields() {}

    protected fun onStartFromNewState() {}

    protected fun onRestoreFromSavedState(savedInstanceState: Bundle) {}

    protected fun onImplFields() {}
}
