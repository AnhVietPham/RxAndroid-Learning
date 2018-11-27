package com.example.anhvietpham.type_of_operators.from

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers



class Example9Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val numbers = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        val numbers1 = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15)

        Observable.fromIterable(numbers)
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
                    Log.e("TAG", "onNext: $t")
                }

                override fun onError(e: Throwable) {
                    //TODO: onError
                }

            })
    }
}
