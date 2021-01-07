package com.mdkhaki.rxplayground

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mdkhaki.rxplayground.repository.Repository
import okhttp3.ResponseBody


class MainViewModel : ViewModel() {
    private val repository: Repository = Repository.instance!!
    fun makeQuery(): LiveData<ResponseBody> {
        return repository.makeReactiveQuery()
    }

}