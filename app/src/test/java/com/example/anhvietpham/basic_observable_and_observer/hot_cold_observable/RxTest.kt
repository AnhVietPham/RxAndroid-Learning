package com.example.anhvietpham.basic_observable_and_observer.hot_cold_observable

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test

class RxTest {
    @Test
    fun testColdObservable() {
        println("Before Observable")
        val coldObservable = Observable
            .just(true)
            .map { t ->
                return@map t
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        val subscription = coldObservable
            .subscribe {
                println("Call with return value $it")
            }

        println("Right after Observable")
    }
}