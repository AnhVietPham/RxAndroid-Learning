package com.example.anhvietpham.observables.maybe_maybeobserver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class Example13Activity : AppCompatActivity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteObservable = getNoteObservable()

        val noteObserver = getNoteObserver()

        noteObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(noteObserver)
    }

    private fun getNoteObserver(): MaybeObserver<Note> {
        return object : MaybeObserver<Note> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onSuccess(note: Note) {
                Log.e("TAG", "onSuccess: " + note.name)
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
            }

            override fun onComplete() {
                Log.e("TAG", "onComplete")
            }
        }
    }

    private fun getNoteObservable(): Maybe<Note> {
        return Maybe.create { emitter ->
            val note = Note(1, "Call brother!")
            if (!emitter.isDisposed) {
                emitter.onSuccess(note)
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