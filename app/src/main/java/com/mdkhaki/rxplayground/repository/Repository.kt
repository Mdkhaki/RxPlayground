package com.mdkhaki.rxplayground.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.mdkhaki.rxplayground.network.ServiceGenerator
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody


class Repository {
    fun makeReactiveQuery(): LiveData<ResponseBody> {
        return LiveDataReactiveStreams.fromPublisher(
            ServiceGenerator.requestApi.makeQuery()!!.subscribeOn(Schedulers.io())
        )
    }

    companion object {
        var instance: Repository? = null
            get() {
                if (field == null) {
                    field = Repository()
                }
                return field
            }
            private set
    }
}