package com.example.anhvietpham.operator.debounce

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.anhvietpham.basic_observable_and_observer.R
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_debounce_operator.*
import java.util.concurrent.TimeUnit


class DebounceOperatorActivity : AppCompatActivity() {
    private val TAG = DebounceOperatorActivity::class.java.simpleName
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debounce_operator)
        compositeDisposable.add(
            RxTextView.textChangeEvents(input_search)
                .skipInitialValue()
                .debounce(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchQuery())
        )

        txt_search_string.text = "Search query will be accumulated every 300 milli sec"
    }

    private fun searchQuery(): DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>() {
            override fun onComplete() {
            }

            override fun onNext(t: TextViewTextChangeEvent) {
                Log.e("DebounceOperator", "search string: " + t.text().toString())
                txt_search_string.text = "Query: " + t.text().toString()
            }

            override fun onError(e: Throwable) {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}