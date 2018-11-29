package com.example.anhvietpham.observables.observable_observer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class Example11Activity : AppCompatActivity() {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notesObservable = getNotesObservable()
        val notesObserver = getNotesObserver()
        notesObservable.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeWith(notesObserver)
    }

    private fun getNotesObserver(): Observer<Note> {
        return object : Observer<Note>{
            override fun onComplete() {
                Log.e("TAG", "onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                Log.e("TAG", "onSubscribe")
                disposable = d
            }

            override fun onNext(t: Note) {
                Log.e("TAG", "onNext: " + t.name)
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
            }

        }
    }

    private fun getNotesObservable() : Observable<Note>{
        val notes = preparenotes()
        return Observable.create {
            for (note in notes) {
                if (!it.isDisposed) {
                    it.onNext(note)
                }
            }
            // all notes are emitted
            if (!it.isDisposed) {
                it.onComplete()
            }
        }
    }

    private fun preparenotes() : List<Note>{
        val notes = mutableListOf<Note>()
        notes.add(Note(1, "Buy tooth paste!"))
        notes.add(Note(2, "Call brother!"))
        notes.add(Note(3, "Watch Narcos tonight!"))
        notes.add(Note(4, "Pay power bill!"))
        return notes
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