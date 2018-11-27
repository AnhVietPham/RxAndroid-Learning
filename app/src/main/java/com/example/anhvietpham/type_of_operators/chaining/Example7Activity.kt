package com.example.anhvietpham.type_of_operators.chaining

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Example7Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Observable.range(1,20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { t ->
                t % 2 == 0
            }
            .map { t ->
                t.toString() + " is even number"
            }
            .subscribe(object : Observer<String>{
                override fun onComplete() {
                    Log.d("TAG", "All numbers emitted!");
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: String) {
                    Log.e("TAG", "onNext: $t");
                }

                override fun onError(e: Throwable) {
                }

            })
    }
}