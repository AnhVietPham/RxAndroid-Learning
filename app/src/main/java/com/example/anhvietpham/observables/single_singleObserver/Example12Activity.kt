package com.example.anhvietpham.observables.single_singleObserver

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.SingleObserver
import io.reactivex.internal.util.NotificationLite.disposable
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe







class Example12Activity : AppCompatActivity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noteObservable = getNoteObservable()

        val singleObserver = getSingleObserver()

        noteObservable
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(singleObserver)
    }

    private fun getSingleObserver(): SingleObserver<Note> {
        return object : SingleObserver<Note> {
            override fun onSubscribe(d: Disposable) {
                Log.d("TAG", "onSubscribe")
                disposable = d
            }

            override fun onSuccess(note: Note) {
                Log.e("TAG", "onSuccess: " + note.name)
            }

            override fun onError(e: Throwable) {
                Log.d("TAG", "onError: " + e.message)
            }
        }
    }

    private fun getNoteObservable(): Single<Note> {
        return Single.create { emitter ->
            val note = Note(1, "Buy milk!")
            emitter.onSuccess(note)
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