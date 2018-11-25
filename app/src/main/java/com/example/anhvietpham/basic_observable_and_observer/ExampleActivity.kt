package com.example.anhvietpham.basic_observable_and_observer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ExampleActivity : AppCompatActivity() {
    private val TAG = ExampleActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                Log.e(TAG, "All items are emitted!")
            }

            override fun onSubscribe(d: Disposable) {
                Log.e(TAG, "onSubscribe")
            }

            override fun onNext(t: String) {
                Log.e(TAG, "Name: $t")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }
        }
    }
}
