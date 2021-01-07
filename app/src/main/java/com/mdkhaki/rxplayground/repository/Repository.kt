package com.mdkhaki.rxplayground.repository

import com.mdkhaki.rxplayground.network.ServiceGenerator
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.*


class Repository {

    companion object {
        var instance: Repository ? =  null
            get() {
                return  field ?: Repository()
            }
    }

    fun makeFutureQuery(): Future<Observable<ResponseBody>> {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        val myNetworkCallable: Callable<Observable<ResponseBody>> =
            Callable<Observable<ResponseBody>> { ServiceGenerator.requestApi.makeObservableQuery() }
        return object : Future<Observable<ResponseBody>> {
            override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
                if (mayInterruptIfRunning) {
                    executor.shutdown()
                }
                return false
            }
            override fun isDone(): Boolean {
                return executor.isTerminated
            }

            override fun isCancelled(): Boolean {
                return executor.isShutdown
            }

            @Throws(ExecutionException::class, InterruptedException::class)
            override fun get(): Observable<ResponseBody> {
                return executor.submit(myNetworkCallable).get()
            }

            @Throws(ExecutionException::class,
                InterruptedException::class,
                TimeoutException::class)
            override fun get(timeout: Long, unit: TimeUnit?): Observable<ResponseBody> {
                return executor.submit(myNetworkCallable).get(timeout, unit)
            }
        }
    }
}