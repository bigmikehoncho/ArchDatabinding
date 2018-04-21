package com.bigmikehoncho.sample.di

import com.bigmikehoncho.sample.SampleApp
import com.bigmikehoncho.sample.view.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidModule::class)])
interface AppComponent {
    fun inject(application: SampleApp)

    fun inject(mainActivity: MainActivity)

}