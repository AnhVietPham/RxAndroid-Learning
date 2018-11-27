package com.example.anhvietpham.type_of_operators.range

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class Example6Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Observable.range(1,20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<Int>() {
                override fun onComplete() {
                    Log.e("TAG", "All numbers emitted!")
                }

                override fun onNext(t: Int) {
                    Log.e("TAG", "Number: $t")
                }

                override fun onError(e: Throwable) {

                }

            })
    }
}