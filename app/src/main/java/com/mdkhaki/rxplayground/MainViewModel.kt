package com.mdkhaki.rxplayground

import androidx.lifecycle.ViewModel
import com.mdkhaki.rxplayground.repository.Repository
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.Future


class MainViewModel : ViewModel() {
    private val repository: Repository = Repository.instance!!

    fun makeFutureQuery(): Future<Observable<ResponseBody>> {
        return repository.makeFutureQuery()
    }

}