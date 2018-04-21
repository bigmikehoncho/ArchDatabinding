package com.bigmikehoncho.mvvmdatabinding

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by mike on 8/26/17.
 */

class BaseVM : ViewModel() {

    protected var viewState: LiveData<ViewState<*>>? = null
}
