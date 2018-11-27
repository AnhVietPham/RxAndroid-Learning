package com.example.anhvietpham.custom_data_type_operators

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class Example5Activity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        compositeDisposable?.add(getNotesObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { note ->
                // Making the note to all uppercase
                note.name = note.name.toUpperCase()
                note
            }
            .subscribeWith(getNotesObserver()))
    }

    private fun getNotesObserver(): DisposableObserver<Note> {
        return object : DisposableObserver<Note>() {

            override fun onNext(note: Note) {
                Log.e("TAG", "Note: " + note.name)
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d("TAG", "All notes are emitted!")
            }
        }
    }

    private fun getNotesObservable(): Observable<Note> {
        val notes = prepareNotes()

        return Observable.create { emitter ->
            for (note in notes) {
                if (!emitter.isDisposed) {
                    emitter.onNext(note)
                }
            }

            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }
    }

    private fun prepareNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        notes.add(Note(1, "buy tooth paste!"))
        notes.add(Note(2, "call brother!"))
        notes.add(Note(3, "watch narcos tonight!"))
        notes.add(Note(4, "pay power bill!"))

        return notes
    }

    class Note(
        val id: Int,
        var name: String
    )

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}