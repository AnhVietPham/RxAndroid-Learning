package com.example.anhvietpham.disposable

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class Example2Activity : AppCompatActivity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animalsObservable = Observable.just("Ant", "Bee", "Cat", "Dog", "Fox")
        val animalsObserver = getAnimalsObserver()
        animalsObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(animalsObserver)
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

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "onDestroy")
        // don't send events once the activity is destroyed
        disposable?.dispose()
    }
}