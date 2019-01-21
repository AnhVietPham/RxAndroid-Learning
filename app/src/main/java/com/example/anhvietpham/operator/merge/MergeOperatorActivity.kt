package com.example.anhvietpham.operator.merge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.anhvietpham.operator.concatmap.ConcatMapOperatorActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MergeOperatorActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mergerObservable()
    }
    private fun mergerObservable(){
        Observable
            .merge(getMaleObservable(),getFemaleObservable())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<User> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: User) {
                    Log.e("MergeObservable", t.name + ", " + t.gender)
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    private fun getMaleObservable(): Observable<User> {
        val maleUsers = arrayOf("Mark", "John", "Trump","Obama")

        val users = mutableListOf<User>()

        for (name in maleUsers) {
            val user = User()
            user.name = name
            user.gender = "male"

            users.add(user)
        }

        return Observable
            .create(ObservableOnSubscribe<User> { emitter ->
                for (user in users) {
                    if (!emitter.isDisposed) {
                        Thread.sleep(1000)
                        emitter.onNext(user)
                    }
                }

                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            }).subscribeOn(Schedulers.io())
    }

    private fun getFemaleObservable(): Observable<User> {
        val maleUsers = arrayOf("Lucy", "Scarlett", "April")

        val users = mutableListOf<User>()

        for (name in maleUsers) {
            val user = User()
            user.name = name
            user.gender = "Famle"

            users.add(user)
        }

        return Observable
            .create(ObservableOnSubscribe<User> { emitter ->
                for (user in users) {
                    if (!emitter.isDisposed) {
                        Thread.sleep(800)
                        emitter.onNext(user)
                    }
                }

                if (!emitter.isDisposed) {
                    emitter.onComplete()
                }
            }).subscribeOn(Schedulers.io())
    }

    class User(
        var name: String = "",
        var email: String = "",
        var gender: String = "",
        var address: Address? = null
    )
    class Address(var city: String = "")
}