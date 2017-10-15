package com.bigmikehoncho.mvvmdatabinding.utils

import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposables

@NonNull
fun <T> toObservable(@NonNull field: ObservableField<T>): Observable<T> {
    return Observable.create { e ->
        val value = field.get()
        if (value != null) {
            e.onNext(value)
        }
        val callback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
                e.onNext(field.get())
            }
        }
        field.addOnPropertyChangedCallback(callback)
        e.setDisposable(Disposables.fromAction { field.removeOnPropertyChangedCallback(callback) })
    }
}

@NonNull
fun toObservable(@NonNull field: ObservableBoolean): Observable<Boolean> {
    return Observable.create { e ->
        e.onNext(field.get())
        val callback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
                e.onNext(field.get())
            }
        }
        field.addOnPropertyChangedCallback(callback)
        e.setDisposable(Disposables.fromAction { field.removeOnPropertyChangedCallback(callback) })
    }
}

@NonNull
fun toObservable(@NonNull field: ObservableInt): Observable<Int> {
    return Observable.create { e ->
        e.onNext(field.get())
        val callback = object : OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: android.databinding.Observable, i: Int) {
                e.onNext(field.get())
            }
        }
        field.addOnPropertyChangedCallback(callback)
        e.setDisposable(Disposables.fromAction { field.removeOnPropertyChangedCallback(callback) })
    }
}

/**
 * A convenient wrapper for `ReadOnlyField#create(Observable)`
 *
 * @return DataBinding field created from the specified Observable
 */
@NonNull
fun <T> toField(@NonNull observable: Observable<T>): ReadOnlyField<T> = ReadOnlyField.create(observable)

@NonNull
fun toBooleanField(@NonNull observable: Observable<Boolean>): ReadOnlyBoolean = ReadOnlyBoolean.create(observable)

