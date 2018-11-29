package com.example.anhvietpham.observables.flowable_observer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Example15Activity : AppCompatActivity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val flowableObservable = getFlowableObservable()

        val observer = getFlowableObserver()

        flowableObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .reduce(0) { t1, t2 -> t1 + t2 }
            .subscribe(observer)
    }

    private fun getFlowableObserver(): SingleObserver<Int> {
        return object : SingleObserver<Int> {
            override fun onSuccess(t: Int) {
                Log.e("TAG", "onSuccess: $t")
            }
            override fun onSubscribe(d: Disposable) {
                Log.e("TAG", "onSubscribe")
                disposable = d
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
            }
        }
    }


    private fun getFlowableObservable(): Flowable<Int> {
        return Flowable.range(1, 100)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}