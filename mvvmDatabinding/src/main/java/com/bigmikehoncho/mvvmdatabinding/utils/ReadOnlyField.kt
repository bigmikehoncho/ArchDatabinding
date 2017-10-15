package com.bigmikehoncho.mvvmdatabinding.utils

import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableField
import android.util.Log
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

class ReadOnlyField<T> protected constructor(@NonNull source: Observable<T>) : ObservableField<T>() {
    internal val source: Observable<T>
    internal val disposables = HashMap<OnPropertyChangedCallback, Disposable>()

    init {
        this.source = source
                .doOnNext { t -> super@ReadOnlyField.set(t) }
                .doOnError { throwable -> Log.e("ReadOnlyField", "onError in source observable", throwable) }
                .onErrorResumeNext(Observable.empty())
                .share()
    }


    @Deprecated("Setter of ReadOnlyField does nothing. Merge with the source Observable instead.\n" +
            "      See <a href=\"https://github.com/manas-chaudhari/android-mvvm/tree/master/Documentation/ObservablesAndSetters.md\">Documentation/ObservablesAndSetters.md</a>")
    override fun set(value: T) {
    }

    @Synchronized override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        super.addOnPropertyChangedCallback(callback)
        disposables.put(callback, source.subscribe())
    }

    @Synchronized override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback) {
        super.removeOnPropertyChangedCallback(callback)
        val disposable = disposables.remove(callback)
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    companion object {

        fun <U> create(@NonNull source: Observable<U>): ReadOnlyField<U> {
            return ReadOnlyField(source)
        }
    }
}
