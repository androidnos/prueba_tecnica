package com.example.prueba_tecnica.base.presenter

import com.example.prueba_tecnica.retrofit.APIClient
import com.example.prueba_tecnica.retrofit.APIInterface
import okhttp3.Cache

abstract class BasePresenter<T> : IBasePresenter<T> {

    open var view: T? = null
    open lateinit var apiInterface: APIInterface

    override fun attactView(view: T, cache: Cache) {
        this.view = view
        apiInterface = APIClient.getClient(cache)
            .create(APIInterface::class.java)
    }
}