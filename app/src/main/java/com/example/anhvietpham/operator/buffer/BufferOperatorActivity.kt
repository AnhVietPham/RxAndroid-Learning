package com.example.anhvietpham.operator.buffer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.anhvietpham.basic_observable_and_observer.R
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class BufferOperatorActivity : AppCompatActivity() {
    private val TAG = BufferOperatorActivity::class.java.simpleName
    private var disposable: Disposable? = null
    private var maxTaps = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxView.clicks(layout_tap_area)
            .map {
                1
            }
            .buffer(3,TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<List<Int>>{
                override fun onComplete() {
                    Log.e(TAG, "onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: List<Int>) {
                    Log.e(TAG, "onNext: " + t.size + " taps received!")
                    if (t.isNotEmpty()) {
                        maxTaps = if (t.size > maxTaps) t.size else maxTaps
                        tap_result.text = String.format("Received %d taps in 3 secs", t.size)
                        tap_result_max_count.text = String.format("Maximum of %d taps received in this session", maxTaps)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: " + e.message)
                }

            })

        val integerObservable = Observable.just(
            1, 2, 3, 4,
            5, 6, 7, 8, 9
        )

        integerObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(3)
            .subscribe(object : Observer<List<Int>>{
                override fun onComplete() {
                    Log.e(TAG, "All items emitted!")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe")
                    disposable = d
                }

                override fun onNext(t: List<Int>) {
                    Log.e(TAG, "onNext")
                    for (integer in t) {
                        Log.e(TAG, "Item: $integer")
                    }
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}