package com.example.anhvietpham.type_of_operators.just

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Example8Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int>{
                override fun onComplete() {
                    //TODO: onComplete
                }

                override fun onSubscribe(d: Disposable) {
                    //TODO: onSubscribe
                }

                override fun onNext(t: Int) {
                    Log.e("TAG", "onNext: $t");
                }

                override fun onError(e: Throwable) {
                    //TODO: onError
                }

            })

        val numbers = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        val numbers1 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 16, 20)

        Observable.just(numbers, numbers1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Array<Int>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(integers: Array<Int>) {
                    Log.e("TAG", "onNext: " + integers.size)
                    // you might have to loop through the array
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }
}