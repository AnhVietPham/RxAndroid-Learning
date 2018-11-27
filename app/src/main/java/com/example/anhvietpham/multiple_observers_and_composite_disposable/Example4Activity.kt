package com.example.anhvietpham.multiple_observers_and_composite_disposable

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class Example4Activity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animalsObservable = getAnimalsObservable()
        val animalsObserver = getAnimalsObserver()
        val animalsObserverAllCaps = getAnimalsAllCapsObserver()
        compositeDisposable = CompositeDisposable()

        compositeDisposable?.add(
            animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter{t -> t.toLowerCase().startsWith("b")}
                .subscribeWith(animalsObserver)
        )

        compositeDisposable?.add(
            animalsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter{t -> t.toLowerCase().startsWith("c") }
                .map{s -> s.toUpperCase() }
                .subscribeWith(animalsObserverAllCaps)
        )
    }

    private fun getAnimalsObserver(): DisposableObserver<String> {
        return object : DisposableObserver<String>() {

            override fun onNext(s: String) {
                Log.e("TAG", "Name: $s")
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
            }

            override fun onComplete() {
                Log.e("TAG", "All items are emitted!")
            }
        }
    }

    private fun getAnimalsAllCapsObserver(): DisposableObserver<String> {
        return object : DisposableObserver<String>() {

            override fun onNext(s: String) {
                Log.e("TAG", "Name: $s")
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
            }

            override fun onComplete() {
                Log.e("TAG", "All items are emitted!")
            }
        }
    }

    private fun getAnimalsObservable(): Observable<String> {
        return Observable.fromArray(
            "Ant", "Ape",
            "Bat", "Bee", "Bear", "Butterfly",
            "Cat", "Crab", "Cod",
            "Dog", "Dove",
            "Fox", "Frog"
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // don't send events once the activity is destroyed
        compositeDisposable?.clear()
    }
}