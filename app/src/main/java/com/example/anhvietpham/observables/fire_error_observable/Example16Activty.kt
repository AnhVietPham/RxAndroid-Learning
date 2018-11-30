package com.example.anhvietpham.observables.fire_error_observable

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.processors.PublishProcessor
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


class Example16Activty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this))
        //testColdBehavior()
        testHotBehavior()
        //testBackpressureHot()
    }

    private fun testHotBehavior() {
        val source: PublishProcessor<Long> = PublishProcessor.create<Long>()

        val subscribeWith = source.subscribeOn(Schedulers.io())
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
                Log.d("Example16Activty", "1 onComplete")
            }

            override fun onSubscribe(s: Subscription?) {
                //TODO: onSubscribe
                Log.d("Example16Activty", "1 onSubscribe")
            }

            override fun onNext(t: Long?) {
                //Thread.sleep(100)
                Log.d("Example16Activty", "1 onNext $t")
            }

            override fun onError(t: Throwable?) {
                //TODO: onError
                Log.d("Example16Activty", "1 $t")
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
                Log.d("Example16Activty", "2 onNext $t")
            }

            override fun onError(t: Throwable?) {
                //TODO: onError
            }

        }
    }

    private fun testColdBehavior() {
        val observables: Observable<Long> = getObservable()
        val observer1 = getObserver1()
        val observer2 = getObserver2()

        observables.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(observer1)

        observables.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(observer2)
    }

    private fun testBackpressureHot() {
        val source = PublishProcessor.create<Int>()
        source.observeOn(Schedulers.computation())
            .subscribe({ v ->
                compute(v)
            }, { e ->
                e.printStackTrace()
            })
        for (i in 0..999999) {
            source.onNext(i)
        }
        Thread.sleep(10000)
    }

    private fun compute(v: Int)  {

        //TODO: compute
    }

    private fun getObserver1(): Observer<Long> {
        return object : Observer<Long>{
            override fun onComplete() {
                Log.e("Example16Activty", "All emit success")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Long) {
                Thread.sleep(100)
                Log.d("Example16Activty", "1 onNext $t")
            }

            override fun onError(e: Throwable) {
                Log.d("Example16Activty", e.message)
            }
        }
    }

    private fun getObserver2(): Observer<Long> {
        return object : Observer<Long>{
            override fun onComplete() {
                Log.e("Example16Activty", "All emit success")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Long) {
                Thread.sleep(200)
                Log.d("Example16Activty", "2 onNext $t")
            }

            override fun onError(e: Throwable) {
                Log.d("Example16Activty", e.message)
            }
        }
    }

    private fun getObservable(): Observable<Long> {
        return Observable.rangeLong(1, 10)
    }

}