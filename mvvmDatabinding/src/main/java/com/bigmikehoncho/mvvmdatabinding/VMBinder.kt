package com.bigmikehoncho.mvvmdatabinding

import android.arch.lifecycle.ViewModel
import android.support.annotation.LayoutRes

/**
 * Created by mike on 3/4/17.
 */

interface VMBinder {

    @get:LayoutRes
    val layoutId: Int

    fun createViewModel(): ViewModel
}
