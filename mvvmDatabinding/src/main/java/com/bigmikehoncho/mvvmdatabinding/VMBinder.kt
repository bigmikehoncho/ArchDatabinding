package com.bigmikehoncho.mvvmdatabinding

import android.arch.lifecycle.ViewModel
import android.support.annotation.LayoutRes

interface VMBinder {

    @get:LayoutRes
    val layoutId: Int

    fun provideViewModelClass(): Class<out ViewModel>
}
