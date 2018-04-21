package com.bigmikehoncho.mvvmdatabinding.utils

import android.arch.lifecycle.ViewModel
import android.databinding.BindingConversion
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.annotation.LayoutRes
import com.bigmikehoncho.mvvmdatabinding.adapters.ViewProvider
import io.reactivex.Observable
import java.util.*


@BindingConversion
fun getViewProviderForStaticLayout(@LayoutRes layoutId: Int): ViewProvider {
    return object : ViewProvider {
        override fun getView(vm: ViewModel) = layoutId
    }
}

@BindingConversion
fun <VM : ViewModel> toGenericList(specificList: Observable<List<VM>>?): Observable<List<VM>>? {
    return specificList?.map { ts -> ArrayList(ts) }
}

@BindingConversion
fun <VM : ViewModel> toObservableList(specificList: List<VM>?): ObservableList<VM>? {
    return if (specificList == null) {
        null
    } else {
        val observableList = ObservableArrayList<VM>()
        observableList.addAll(specificList)
        observableList
    }
}