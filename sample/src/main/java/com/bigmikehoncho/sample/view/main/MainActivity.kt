package com.bigmikehoncho.sample.view.main

import com.bigmikehoncho.sample.R
import com.bigmikehoncho.sample.view.common.BaseActivity

class MainActivity : BaseActivity<MainVM>() {

    override fun provideLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun provideViewModelClass(): Class<MainVM> {
        return MainVM::class.java
    }
}
