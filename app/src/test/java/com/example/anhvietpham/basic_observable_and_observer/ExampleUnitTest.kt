package com.example.anhvietpham.basic_observable_and_observer

import android.util.Log
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        //testHotBehavior()
        testBackpressureHot()
    }

    private fun testHotBehavior() {
        val source: PublishProcessor<Long> = PublishProcessor.create<Long>()
        source.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(getSubscriber1())

        /*val subscribeWith1 = source.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeWith(getSubscriber2())*/

        for (i in 0..10) {
            source.onNext(i.toLong())
        }
    }

    private fun getSubscriber1(): Subscriber<Long> {
        return object : Subscriber<Long> {
            override fun onComplete() {
                //TODO: onComplete
                System.out.println("1 onComplete")
            }

            override fun onSubscribe(s: Subscription?) {
                //TODO: onSubscribe
                System.out.println("1 onSubscribe")
            }

            override fun onNext(t: Long?) {
                //Thread.sleep(100)
                System.out.println("1 onNext $t")
            }

            override fun onError(t: Throwable?) {
                //TODO: onError
                System.out.println("1 $t")
            }

        }
    }

    private fun getSubscriber2(): Subscriber<Long> {
        return object : Subscriber<Long> {
            override fun onComplete() {
                //TODO: onComplete
            }

            override fun onSubscribe(s: Subscription?) {
                //TODO: onSubscribe
            }

            override fun onNext(t: Long?) {
                Thread.sleep(200)
                System.out.println("2 onNext $t")
            }

            override fun onError(t: Throwable?) {
                //TODO: onError
            }

        }
    }

    private fun testBackpressureHot() {
        val source = PublishProcessor.create<Int>()
        source.observeOn(Schedulers.computation())
            .subscribe({ v ->
                compute(v)
            })

        for (i in 0..10) {
            source.onNext(i)
            if (i == 4) {
                source.observeOn(Schedulers.io())
                    .subscribe({ v ->
                        compute2(v)
                    })
            }
        }
    }

    private fun compute(v: Int?) {
        //Thread.sleep(10)
        System.out.println("compute 1: $v")
    }

    private fun compute2(v: Int?) {
        //Thread.sleep(20)
        System.out.println("compute 2: $v")
    }
}
