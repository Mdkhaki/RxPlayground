package com.mdkhaki.rxplayground

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mdkhaki.rxplayground.data.DataSource
import com.mdkhaki.rxplayground.models.Task
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"

    // vars
    val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val taskObservable: Observable<Task> = Observable
            .fromIterable(DataSource.createTasksList())
            .subscribeOn(Schedulers.io())
            .filter(Predicate {
                it.isComplete
            })
            .observeOn(AndroidSchedulers.mainThread())


        taskObservable.subscribe(object :
            Observer<Task> {
            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: ")
                disposables.add(d)
            }

            override fun onNext(t: Task) {
                Log.d(TAG, "onNext: " + Thread.currentThread().name)
                Log.d(TAG, "onNext: " + t.description)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: " + e.message)
            }
        })

    }


    override fun onDestroy() {
        disposables.clear()
        // hard clear
        disposables.dispose()
        super.onDestroy()
    }
}