package com.mdkhaki.rxplayground.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET


public interface RequestApi {
    @GET("todos/1")
    fun makeObservableQuery(): Observable<ResponseBody>
}