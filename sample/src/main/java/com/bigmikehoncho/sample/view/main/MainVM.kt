package com.bigmikehoncho.sample.view.main

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList

/**
 * Created by mike on 10/5/17.
 */

class MainVM : ViewModel(){

    var vms : ObservableList<ViewModel> = ObservableArrayList()

    fun init(){
    }
}
