package com.mdkhaki.rxplayground.network

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET


public interface RequestApi {
    @GET("todos/1")
    fun makeObservableQuery(): Flowable<ResponseBody>


    @GET("todos/1")
    fun makeQuery(): Flowable<ResponseBody>?
}