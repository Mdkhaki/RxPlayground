package com.mdkhaki.rxplayground.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    companion object{
        private val BASE_URL = "https://jsonplaceholder.typicode.com"

        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit: Retrofit = retrofitBuilder.build()

        val requestApi: RequestApi = retrofit.create(RequestApi::class.java)
    }

}