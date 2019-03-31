package com.example.anhvietpham.hot_cold_observable

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
/*
* Test merger branch github
* Create new branch
* Createing new branch 2
* */
class ColdAndHotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // testColdObservable()
        testHotObservable()
    }

    private fun testColdObservable() {
        Log.e("ColdAndHotActivity", "Before Observable")
        val coldObservable = Observable
            .just(true)
            .map { t ->
                Log.e("ColdAndHotActivity", "Within observable")
                return@map t
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val subscription = coldObservable
            .subscribe {
                Log.e("ColdAndHotActivity", "Call with return value $it")
            }

        Log.e("ColdAndHotActivity", "Right after Observable")
    }

    private fun testHotObservable(){
        Log.e("ColdAndHotActivity", "Before Observable")
        val observable = Observable
            .just(1)
            .publish()

        observable.connect()

        observable.subscribe {
            Log.e("ColdAndHotActivity", "Call with return value $it")
        }

        Log.e("ColdAndHotActivity", "Right after Observable")
    }
}
/*
* Add merger
* */
/*
* Add comment
* */