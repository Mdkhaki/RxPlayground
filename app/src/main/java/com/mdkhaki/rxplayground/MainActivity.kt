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

        /** Observables */

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
                Log.d(TAG, "Observables || onComplete: ")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "Observables || onSubscribe: ")
                disposables.add(d)
            }

            override fun onNext(t: Task) {
                Log.d(TAG, "Observables || onNext: " + Thread.currentThread().name)
                Log.d(TAG, "Observables || onNext: " + t.description)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Observables || onError: " + e.message)
            }
        })

        /** Create Operator */

        // Create the Observable
        val taskListObservable =
            Observable
                .create<Task> { emitter -> // Inside the subscribe method iterate through the list of tasks and call onNext(task)
                    for (task in DataSource.createTasksList()) {
                        if (!emitter.isDisposed) {
                            emitter.onNext(task!!)
                        }
                    }
                    // Once the loop is complete, call the onComplete() method
                    if (!emitter.isDisposed) {
                        emitter.onComplete()
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :
                    Observer<Task> {
                    override fun onComplete() {
                        Log.d(TAG, "Create || onComplete: ")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "Create || onSubscribe: ")
                    }

                    override fun onNext(t: Task) {
                        Log.d(TAG, "Create || onNext: task list: " + t.description)
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "Create || onError: ")
                    }

                })

        /** Just Operator */

        val taskJustObservable =
            Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :
                    Observer<Int> {
                    override fun onComplete() {
                        Log.d(TAG, "Just || onComplete: ")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "Just || onSubscribe: ")
                    }

                    override fun onNext(t: Int) {
                        Log.d(TAG, "Just || onNext: ")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "Just || onError: ")
                    }

                }
                )

        /** Range Operator */

        val taskRangeObservable =
            Observable.range(1,10)
//                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :
                    Observer<Int> {
                    override fun onComplete() {
                        Log.d(TAG, "Range || onComplete: ")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "Range || onSubscribe: ")
                    }

                    override fun onNext(t: Int) {
                        Log.d(TAG, "Range || onNext: ")
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "Range || onError: ")
                    }

                }
                )

    }


    override fun onDestroy() {
        disposables.clear()
        // hard clear
//        disposables.dispose()
        super.onDestroy()
    }
}