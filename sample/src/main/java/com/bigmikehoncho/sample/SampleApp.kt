package com.bigmikehoncho.sample

import android.app.Application
import com.bigmikehoncho.mvvmdatabinding.utils.BindingUtils
import com.bigmikehoncho.sample.di.AndroidModule
import com.bigmikehoncho.sample.di.AppComponent
import com.bigmikehoncho.sample.di.DaggerAppComponent
import com.bigmikehoncho.sample.view.bindings.BindingAdapters
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // LEAKCANARY INIT
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not initBaseData your app in this process.
            return
        }
        refWatcher = LeakCanary.install(this)

        instance = this

        if (BuildConfig.DEBUG) {
            // TIMBER INIT
            Timber.plant(Timber.DebugTree())
        }

        @Suppress("DEPRECATION")
        appComponent = DaggerAppComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
        appComponent.inject(this)

        // DATA BINDING INIT
        BindingUtils.defaultBinder = BindingAdapters.defaultBinder
    }

    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var appComponent: AppComponent private set
        @JvmStatic lateinit var instance: SampleApp private set
        @JvmStatic lateinit var refWatcher: RefWatcher private set
    }

}