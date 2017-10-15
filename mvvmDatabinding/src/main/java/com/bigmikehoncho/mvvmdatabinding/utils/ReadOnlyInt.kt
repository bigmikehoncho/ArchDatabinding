package com.bigmikehoncho.mvvmdatabinding.utils

import android.databinding.ObservableInt
import android.util.Log
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

class ReadOnlyInt protected constructor(@NonNull source: Observable<Int>) : ObservableInt() {
    internal val source: Observable<Int>
    internal val disposables = HashMap<android.databinding.Observable.OnPropertyChangedCallback, Disposable>()

    init {
        this.source = source
                .doOnNext { t -> super@ReadOnlyInt.set(t) }
                .doOnError { throwable -> Log.e("ReadOnlyInt", "onError in source observable", throwable) }
                .onErrorResumeNext(Observable.empty())
                .share()
    }


    @Deprecated("Setter of ReadOnlyInt does nothing. Merge with the source Observable instead.\n" +
            "      See <a href=\"https://github.com/manas-chaudhari/android-mvvm/tree/master/Documentation/ObservablesAndSetters.md\">Documentation/ObservablesAndSetters.md</a>")
    override fun set(value: Int) {
    }

    @Synchronized override fun addOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)
        disposables.put(callback, source.subscribe())
    }

    @Synchronized override fun removeOnPropertyChangedCallback(callback: android.databinding.Observable.OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)
        val disposable = disposables.remove(callback)
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    companion object {

        fun create(@NonNull source: Observable<Int>): ReadOnlyInt {
            return ReadOnlyInt(source)
        }
    }
}