package com.bigmikehoncho.sample.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * A module for Android-specific dependencies which require a [android.content.Context] or [ ] to create.
 */
@Module
class AndroidModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

}