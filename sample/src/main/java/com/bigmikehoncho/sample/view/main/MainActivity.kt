package com.bigmikehoncho.sample.view.main

import com.bigmikehoncho.sample.R
import com.bigmikehoncho.sample.view.common.BaseActivity

class MainActivity : BaseActivity<MainVM>() {

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun provideViewModelClass(): Class<MainVM> = MainVM::class.java
}
