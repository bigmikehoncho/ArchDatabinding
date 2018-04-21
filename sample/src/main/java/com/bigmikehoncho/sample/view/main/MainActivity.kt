package com.bigmikehoncho.sample.view.main

import android.arch.lifecycle.ViewModel
import com.bigmikehoncho.sample.R
import com.bigmikehoncho.sample.SampleApp
import com.bigmikehoncho.sample.databinding.ActivityMainBinding
import com.bigmikehoncho.sample.view.common.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun componentInjection() {
        SampleApp.appComponent.inject(this)
    }

    override val layoutId = R.layout.activity_main

    override fun provideViewModelClass(): Class<out ViewModel> = MainVM::class.java

    override fun onInitFields() {
        super.onInitFields()

        binding.vm
    }
}
