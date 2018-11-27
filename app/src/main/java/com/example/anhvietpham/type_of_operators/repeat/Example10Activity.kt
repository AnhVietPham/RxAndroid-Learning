package com.example.anhvietpham.type_of_operators.repeat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class Example10Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Observable
            .range(1, 4)
            .repeat(5)
            .subscribe(object : Observer<Int>{
                override fun onComplete() {
                    Log.e("TAG", "Completed")
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