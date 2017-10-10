package com.bigmikehoncho.mvvmdatabinding

import io.reactivex.functions.Consumer
import java.lang.Exception

class ViewState<N : Navigator> {
    var navigator: N? = null
        set(navigator) {
            field = navigator

            tryRunning()
        }
    private var nConsumer: Consumer<N>? = null

    fun apply(nConsumer: Consumer<N>?) {
        this.nConsumer = nConsumer

        tryRunning()
    }

    private fun tryRunning() {
        if (this.navigator != null && nConsumer != null) {
            try {
                this.nConsumer!!.accept(this.navigator!!)
                this.nConsumer = null
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
