package com.example.anhvietpham.operator.switchmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SwitchMapOperatorActivity : AppCompatActivity() {
    private val TAG = SwitchMapOperatorActivity::class.java.simpleName
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val integerObservable: Observable<Int> = Observable.fromArray(1, 2, 3, 4, 5, 6, 12, 25)
        integerObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .switchMap { t ->
                Observable.just(t).delay(1,TimeUnit.SECONDS)
            }
            .subscribe(object : Observer<Int>{
                override fun onComplete() {
                    Log.e(TAG, "All users emitted!");
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe")
                    disposable = d
                }

                override fun onNext(t: Int) {
                    Log.e(TAG, "onNext: $t")
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

