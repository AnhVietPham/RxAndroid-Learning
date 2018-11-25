package com.example.anhvietpham.operator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class Example3Activity : AppCompatActivity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animalsObservable = getAnimalsObservable()
        val animalsObserver = getAnimalsObserver()
        animalsObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { t -> t.toLowerCase().startsWith("b") }
            .subscribeWith(animalsObserver)
    }

    private fun getAnimalsObserver() : Observer<String> {
        return object : Observer<String> {
            override fun onComplete() {
                Log.e("TAG", "All items are emitted!")
            }

            override fun onSubscribe(d: Disposable) {
                Log.e("TAG", "onSubscribe")
                disposable = d
            }

            override fun onNext(t: String) {
                Log.e("TAG", "Name: $t")
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
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
        disposable?.dispose()
    }
}