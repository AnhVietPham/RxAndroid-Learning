package com.example.anhvietpham.observables.completeble_completableObserver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class Example14Activity : AppCompatActivity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val note = Note(1, "Home work!")

        val completableObservable = updateNote(note)

        val completableObserver = completableObserver()

        completableObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(completableObserver)
    }

    private fun updateNote(note: Note): Completable {
        return Completable.create { emitter ->
            if (!emitter.isDisposed) {
                Thread.sleep(1000)
                emitter.onComplete()
            }
        }
    }

    private fun completableObserver(): CompletableObserver {
        return object : CompletableObserver {
            override fun onSubscribe(d: Disposable) {
                Log.e("TAG", "onSubscribe")
                disposable = d
            }

            override fun onComplete() {
                Log.e("TAG", "onComplete: Note updated successfully!")
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    class Note(
        val id: Int,
        val name: String
    )
}